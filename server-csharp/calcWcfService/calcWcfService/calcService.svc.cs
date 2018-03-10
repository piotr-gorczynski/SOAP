using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;
using System.Xml.Serialization;

namespace calcWcfService
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "calcService" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select calcService.svc or calcService.svc.cs at the Solution Explorer and start debugging.
    public class calcService : ICalc
    {
        [return: SoapElement("result")]
        public double add(double a, double b)
        {
            return a + b;
            //throw new NotImplementedException();
        }

        [return: SoapElement("result")]
        public double div(double a, double b)
        {
            if (b == 0)
                return 0;
            else
                return a / b;
            //throw new NotImplementedException();
        }

        [return: SoapElement("result")]
        public double mul(double a, double b)
        {
            return a * b;
            //throw new NotImplementedException();
        }

        [return: SoapElement("result")]
        public double pow(double a, double b)
        {
            return Math.Pow(a, b);
            //throw new NotImplementedException();
        }

        [return: SoapElement("result")]
        public double sub(double a, double b)
        {
            return a - b;
            //throw new NotImplementedException();
        }
    }
}
