#!/bin/bash
a=1
for i in *.jpg; do
	convert $(printf "%03d.jpg" "$a") -type truecolor bmp/$(printf "%03d.bmp" "$a")
	let a=a+1
	done
