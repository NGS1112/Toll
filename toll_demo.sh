#!/bin/bash
echo "Welcome to the Toll Report demo!"
echo
echo "Select demo type:"
echo

options=("Small" "Medium" "Large" "Custom" "Quit")
select opt in "${options[@]}"
do
	case $opt in
		"Small")
			datafile="data/random1000.txt"
			break
			;;
		"Medium")
			datafile="data/random10000.txt"
			break
			;;
		"Large")
			datafile="data/random100000.txt"
			break
			;;
		"Custom")
			echo "Custom selected, enter file name..."
			echo
			read datafile
			break
			;;
		"Quit")
			exit
			;;
		*)
			echo "Invalid Option"
			;;
	esac
done

if [ -f TollReport.class ]
then
	echo
	echo "Files already compiled, would you like to recompile? [ (Y)es/(N)o ]"
	read answer
	echo
	if [ ${answer,,} == "y" ] || [ ${answer,,} == "yes" ]
	then
		rm *.class
		javac -d . src/*.java
		java TollReport $datafile
	else
		java TollReport $datafile
	fi
else
	javac -d . src/*.java
	java TollReport $datafile
fi