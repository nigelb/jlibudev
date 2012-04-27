#!/bin/bash

#pahole is part of the dwarves package and can be found at:
#http://fedorapeople.org/~acme/dwarves/
#

#pahole `find $1 | grep so$ | grep -v gudev` > $2/libudev-types.h