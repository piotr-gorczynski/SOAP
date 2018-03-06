#!/bin/bash

wsdl=../../wsdl/calc.wsdl

dir=./build

rm -rf ${dir} || exit 1
mkdir ${dir} || exit 1
cd ${dir} || exit 1

wsdl2h -o calc.h ${wsdl} || exit 1

soapcpp2 -j -r -CL calc.h || exit 1

cp ../calcclient.cpp calcclient.cpp || exit 1

g++ -o calc calcclient.cpp soapC.cpp soapcalcProxy.cpp -lgsoap++

