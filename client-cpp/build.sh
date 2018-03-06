#!/bin/bash

wsdl=../../wsdl/calc.wsdl

dir=./client

rm -rf ${dir} || exit 1
mkdir ${dir} || exit 1
cd ${dir} || exit 1

wsdl2h -o calc.h ${wsdl} || exit 1

#soapcpp2 -j -CL calc.h || exit 1

#cp ../calcclient.cpp calcclient.cpp || exit 1

#g++ -o calc calc.c calcclient.c soapC.c -lgsoap++

