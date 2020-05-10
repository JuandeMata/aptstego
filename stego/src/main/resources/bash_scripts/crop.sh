#!/bin/bash
a=1
for i in *.bmp; do
	WIDTH=$(exiftool $(printf "%03d.bmp" "$a") | grep Width | cut -c 35-)
	NEW_WIDTH=$(("$WIDTH"-1))
	HEIGHT=$(exiftool $(printf "%03d.bmp" "$a") | grep Height | cut -c 35-)
	CALCULUS=$(((3 * "$WIDTH") % 4))
	
	if [ "$CALCULUS" -eq "0" ]; then
		echo "cropping $(printf "%03d.bmp" "$a")"
		mogrify -resize "$NEW_WIDTH"x"$HEIGHT" $(printf "%03d.bmp" "$a")
	fi
	let a=a+1
done
