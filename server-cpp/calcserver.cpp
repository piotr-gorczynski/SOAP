#include "soapcalcService.h"
#include "calc.nsmap"

int port = 8080;

int main()
{
  calcService service;
  service.soap->send_timeout = service.soap->recv_timeout = 5; // 5 sec socket idle timeout
  service.soap->transfer_timeout = 30;                         // 30 sec message transfer timeout
  while (service.run(port))
    service.soap_stream_fault(std::cerr);
  service.destroy();
  return 0;
}

/* service operation function */
int calcService::add(double a, double b, double& result)
{
  result = a + b;
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

