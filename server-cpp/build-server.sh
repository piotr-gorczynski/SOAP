#!/bin/bash

dir=./server

rm -rf $dir || exit 1
mkdir $dir
cd $dir

wsdl2h -o calc.h ../../calc.wsdl || exit 1

soapcpp2 -j -SL ../../calc.wsdl || exit 1

cp ../calcserver.cpp calcserver.cpp

g++ -o calcserver calcserver.cpp soapC.cpp soapcalcService.cpp -lgsoap++

