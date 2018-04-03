#include <sys/param.h>
#include <unistd.h>
#include <signal.h>
#include <sys/wait.h>
#include <sys/types.h>
#include <grp.h>
#include "calc.nsmap"
#include "soapcalcService.h" /* generated with soapcpp2 -j calc.h */
// #include "plugin/threads.h"  /* use gsoap/plugin/threads.h portable threads */

void sigchld_handle(int x);
void set_privileges();
void set_timer();
void sigalrm_handle(int x);

int port = 8080;            /* server port */
int num_proc = 0;           /* number of active processes */
int max_proc = 32;          /* max number of active processes */

int main()
{
  calcService service(SOAP_IO_KEEPALIVE);                      /* enable HTTP kee-alive */
  service.soap->accept_timeout = 24*60*60;                     /* quit after 24h of inactivity (optional) */
  service.soap->send_timeout = service.soap->recv_timeout = 1; /* 1 sec socket idle timeout */
  service.soap->transfer_timeout = 5;                          /* 5 sec message transfer timeout */
  SOAP_SOCKET m = service.bind(NULL, port, 100);              /* bind master socket */
  if (soap_valid_socket(m))
  {
    signal(SIGCHLD, sigchld_handle);                           /* catch child process termination */
    while (soap_valid_socket(service.accept()))
    {
      pid_t pid;
      while (num_proc > max_proc)  /* too many active processes, wait to let them terminate */
        sleep(1);
      while ((pid = fork()) == -1) /* fork or retry after sleeping for one second */
        sleep(1);
      if (pid == 0) /* child process */
      {
        service.soap->master = SOAP_INVALID_SOCKET; /* do not close master socket when done */
        set_privileges();                           /* set child process privileges */
        set_timer();                                /* set child process max running time */
        service.serve();
        service.destroy();
        exit(0);
      }
      else /* parent process */
      { 
        ++num_proc;
        service.soap_force_close_socket();           /* parent should close its socket */
        fprintf(stderr, "PID %d started ... ", pid); /* for debugging only */
      }
    }
  }
  service.soap_stream_fault(std::cerr);
  service.destroy(); /* clean up */
  return 0;
}


void sigchld_handle(int x)
{
  for (;;)
  {
    int wstat;
    pid_t pid = waitpid(-1, &wstat, WNOHANG);
    if (pid == 0)
      return;
    if (pid == -1)
      return;
    /* figure out what happened, here we print the results for debugging only */
    if (WIFEXITED(wstat))
      fprintf(stderr, "PID %d returned %d\n", pid, WEXITSTATUS(wstat));
    else if (WIFSIGNALED(wstat))
      fprintf(stderr, "PID %d signalled %d%s\n", pid, WTERMSIG(wstat), WCOREDUMP(wstat) ? "dumped core" : "");
    else if (WIFSTOPPED(wstat))
      fprintf(stderr, "PID %d stopped with signal %d\n", pid, WSTOPSIG(wstat));
    --num_proc;
  }
}


void set_privileges()
{
  gid_t newgid = getgid(), oldgid = getegid();
  uid_t newuid = getuid(), olduid = geteuid();
  if (!olduid)
    setgroups(1, &newgid);
  if (newgid != oldgid)
  {
#if !defined(linux)
    setegid(newgid);
    if (setgid(newgid) == -1)
      abort();
#else
    if (setregid(newgid, newgid) == -1)
      abort();
#endif
  }
  if (newuid != olduid)
  {
#if !defined(linux)
    seteuid(newuid);
    if (setuid(newuid) == -1)
      abort();
#else
    if (setreuid(newuid, newuid) == -1)
      abort();
#endif
  }
}

void set_timer()
{
  /* configure the timer to expire after 5 seconds */
  struct itimerval timer;
  timer.it_value.tv_sec = 5;
  timer.it_value.tv_usec = 0;
  timer.it_interval.tv_sec = 0;
  timer.it_interval.tv_usec = 0;
  signal(SIGALRM, sigalrm_handle);
  setitimer(ITIMER_REAL, &timer, NULL);
}

void sigalrm_handle(int x)
{
  exit(0);
}


/* service operation function */
int calcService::add(double a, double b, double& result)
{
  result = a + b + 1;
  return SOAP_OK;
}

/* service operation function */
int calcService::sub(double a, double b, double& result)
{
  result = a - b;
  return SOAP_OK;
}

/* service operation function */
int calcService::mul(double a, double b, double& result)
{
  result = a * b;
  return SOAP_OK;
}

/* service operation function */
int calcService::div(double a, double b, double& result)
{
  if( b != 0.0 )
    result = a / b;
  else
    return soap_senderfault("Division by zero", NULL);
  return SOAP_OK;
}

/* service operation function */
int calcService::pow(double a, double b, double& result)
{
  result = ::pow(a, b);
  if (soap_errno == EDOM)   /* soap_errno is like errno, but portable */
    return soap_senderfault("Power function domain error", NULL);
  return SOAP_OK;
}

