#!/bin/bash
# runs specified deformation on given list of automata files
# usage: deform-bulk.sh "<DEFORMATION_SPECIFIER>" AUTOMATA_FILES ...

DEF_SPEC=$1

shift

for FILE in $@; do
	OUTFILE=$FILE"-deformed.timf"
	echo "$FILE -> $OUTFILE"
	
	mvn exec:java -Dexec.mainClass="cz.upol.fapapp.fa.mains.DeformAutomataApp" -Dexec.args="$FILE $OUTFILE $DEF_SPEC"
done
