BASE=~/srcAdempiere/globalqss/branches/adempiere355a
for SRC in `find . -name "*.java" | fgrep -v "/X_" | fgrep -v "/I_"`
do
    if [ -s $BASE/$SRC ]
    then
	echo "********** $SRC ***********"
        diff -b $SRC $BASE/$SRC
    fi
done
