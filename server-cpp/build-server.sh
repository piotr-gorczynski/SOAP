#!/bin/bash

wsdl=../../wsdl/calc.wsdl

dir=./server

rm -rf ${dir} || exit 1
mkdir ${dir} || exit 1
cd ${dir} || exit 1

wsdl2h -o calc.h ${wsdl} || exit 1

#soapcpp2 -j -SL ${wsdl} || exit 1

#cp ../calcserver.cpp calcserver.cpp || exit 1

#g++ -o calcserver calcserver.cpp soapC.cpp soapcalcService.cpp -lgsoap++ || exit 1

