﻿//------------------------------------------------------------------------------
// <auto-generated>
//     Ten kod został wygenerowany przez narzędzie.
//     Wersja wykonawcza:4.0.30319.42000
//
//     Zmiany w tym pliku mogą spowodować nieprawidłowe zachowanie i zostaną utracone, jeśli
//     kod zostanie ponownie wygenerowany.
// </auto-generated>
//------------------------------------------------------------------------------

using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Web.Services;
using System.Web.Services.Protocols;
using System.Xml.Serialization;

using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Web;
using System.Text;

// 
// This source code was auto-generated by wsdl, Version=4.6.1055.0.
// 
namespace calcWcfService
{

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("wsdl", "4.6.1055.0")]
    [System.Web.Services.WebServiceBindingAttribute(Name = "calc", Namespace = "http://websrv.cs.fsu.edu/~engelen/calc.wsdl")]
    [ServiceContract(Namespace = "http://websrv.cs.fsu.edu/~engelen/calc.wsdl")]
    public interface ICalc
    {

        /// <remarks/>
        [System.Web.Services.WebMethodAttribute()]
        [System.Web.Services.Protocols.SoapRpcMethodAttribute("", RequestNamespace = "urn:calc", ResponseNamespace = "urn:calc")]
        [return: System.Xml.Serialization.SoapElementAttribute("result")]
        [OperationContract]
        double add(double a, double b);

        /// <remarks/>
        [System.Web.Services.WebMethodAttribute()]
        [System.Web.Services.Protocols.SoapRpcMethodAttribute("", RequestNamespace = "urn:calc", ResponseNamespace = "urn:calc")]
        [return: System.Xml.Serialization.SoapElementAttribute("result")]
        [OperationContract]
        double sub(double a, double b);

        /// <remarks/>
        [System.Web.Services.WebMethodAttribute()]
        [System.Web.Services.Protocols.SoapRpcMethodAttribute("", RequestNamespace = "urn:calc", ResponseNamespace = "urn:calc")]
        [return: System.Xml.Serialization.SoapElementAttribute("result")]
        [OperationContract]
        double mul(double a, double b);

        /// <remarks/>
        [System.Web.Services.WebMethodAttribute()]
        [System.Web.Services.Protocols.SoapRpcMethodAttribute("", RequestNamespace = "urn:calc", ResponseNamespace = "urn:calc")]
        [return: System.Xml.Serialization.SoapElementAttribute("result")]
        [OperationContract]
        double div(double a, double b);

        /// <remarks/>
        [System.Web.Services.WebMethodAttribute()]
        [System.Web.Services.Protocols.SoapRpcMethodAttribute("", RequestNamespace = "urn:calc", ResponseNamespace = "urn:calc")]
        [return: System.Xml.Serialization.SoapElementAttribute("result")]
        [OperationContract]
        double pow(double a, double b);
    }
}