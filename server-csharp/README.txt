Title: Short describtion how to generate SOAP server based on calc WSDL using Microsoft Visual C# 2015
Author: Piotr Gorczynski 2018

1. Create New project using Installed Templates/Visual C#/WCF/WCF Service Application, name it for example calcWcfService
2. Use buil-in wsdl.exe tool to create interface class: wsdl /serverInterface [PATH]\calc.wsdl /o:[PATH]. calcInterfaces.cs file is created with public interface ICalc.
3. Add calcInterfaces.cs file to calcWcfService.
4. Add calcService.svc implementing ICalc.
5. Implement body for all methods: add, div, mul, sub, pow
6. Build solution. You can test it using built-in auto generated client. 
7. To run it as standalone use for example IIS Express, must be launched as admin:  iisexpress /config:[PATH]\applicationhost.config. In the applicationhost.config add following binding:
            <site name="calcWcfService" id="1" serverAutoStart="true">
                <application path="/" applicationPool="Clr4IntegratedAppPool">
                    <virtualDirectory path="/" physicalPath="[PATH]\SOAP\server-csharp\calcWcfService\calcWcfService" />
                </application>
                <bindings>
                    <binding protocol="http" bindingInformation=":62901:" />
                </bindings>
            </site>
8. You can test it using SOAP-UI project: calc-soapui-project.xml
9. If you want to use it from remote, remember to add firewall rules: netsh advfirewall firewall add rule name="IISExpressWeb" dir=in protocol=tcp localport=62901 remoteip=any action=allow
