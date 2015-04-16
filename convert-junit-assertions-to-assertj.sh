#!/bin/bash

function usage() {
  echo
  echo "NAME"
  echo "convert-junit-assertions-to-assertj.sh - Convert most of JUnit assertions to AssertJ assertions"
  echo
  echo "It is difficult to convert ALL JUnit assertions (e.g. the ones that are multiline) but it should be good for most of them."
  echo
  echo "SYNOPSIS"
  echo "convert-junit-assertions-to-assertj.sh [Pattern]"
  echo
  echo "OPTIONS"
  echo " -h --help    this help"
  echo " [Pattern]    a find pattern, default to *Test.java if you don't provide a pattern"
  echo "              don't forget to enclose your pattern with double quotes \"\" to avoid pattern to be expanded by your shell prematurely"
  echo
  echo "EXAMPLE"
  echo " convert-junit-assertions-to-assertj.sh \"*IT.java\""
  exit 0
}

if [ "$1" == "-h" -o "$1" == "--help" ] ;
then
 usage
fi

FILES_PATTERN=${1:-*Test.java}

echo ''
echo "Converting JUnit assertions to AssertJ assertions on files matching pattern : $FILES_PATTERN"
echo ''

FILES=`find . -name "$FILES_PATTERN"`

for EXPRESSION in \
's/assertEquals(\(\".*\"\),[[:blank:]]*0,[[:blank:]]*\(.*\).size())/assertThat(\2).as(\1).isEmpty()/g' \
's/assertEquals(\(\".*\"\),[[:blank:]]*0,[[:blank:]]*\(.*\).size())/assertThat(\2).as(\1).isEmpty()/g' \
's/assertEquals(\(\".*\"\),[[:blank:]]*0,[[:blank:]]*\(.*\).size())/assertThat(\2).as(\1).isEmpty()/g' \
's/assertEquals([[:blank:]]*0,[[:blank:]]*\(.*\).size())/assertThat(\1).isEmpty()/g' \
's/assertEquals(\(\".*\"\),[[:blank:]]*\([[:digit:]]*\),[[:blank:]]*\(.*\).size())/assertThat(\3).as(\1).hasSize(\2)/g' \
's/assertEquals([[:blank:]]*\([[:digit:]]*\),[[:blank:]]*\(.*\).size())/assertThat(\2).hasSize(\1)/g' \
's/assertEquals(\(\".*\"\),[[:blank:]]*\(.*\),[[:blank:]]*\(.*\),[[:blank:]]*\(.*\))/assertThat(\3).as(\1).isCloseTo(\2, within(\4))/g' \
's/assertEquals([[:blank:]]*\([^"]*\),[[:blank:]]*\([^"]*\),[[:blank:]]*\([^"]*\))/assertThat(\2).isCloseTo(\1, within(\3))/g' \
's/assertEquals(\(\".*\"\),[[:blank:]]*\(.*\),[[:blank:]]*\(.*\))/assertThat(\3).as(\1).isEqualTo(\2)/g' \
's/assertEquals([[:blank:]]*\(.*\),[[:blank:]]*\(.*\))/assertThat(\2).isEqualTo(\1)/g' \
's/assertArrayEquals(\(\".*\"\),[[:blank:]]*\(.*\),[[:blank:]]*\(.*\))/assertThat(\3).as(\1).isEqualTo(\2)/g' \
's/assertArrayEquals([[:blank:]]*\(.*\),[[:blank:]]*\(.*\))/assertThat(\2).isEqualTo(\1)/g' \
's/assertNull(\(\".*\"\),[[:blank:]]*\(.*\))/assertThat(\2).as(\1).isNull()/g' \
's/assertNull([[:blank:]]*\(.*\))/assertThat(\1).isNull()/g' \
's/assertNotNull(\(\".*\"\),[[:blank:]]*\(.*\))/assertThat(\2).as(\1).isNotNull()/g' \
's/assertNotNull([[:blank:]]*\(.*\))/assertThat(\1).isNotNull()/g' \
's/assertTrue(\(\".*\"\),[[:blank:]]*\(.*\))/assertThat(\2).as(\1).isTrue()/g' \
's/assertTrue([[:blank:]]*\(.*\))/assertThat(\1).isTrue()/g' \
's/assertFalse(\(\".*\"\),[[:blank:]]*\(.*\))/assertThat(\2).as(\1).isFalse()/g' \
's/assertFalse([[:blank:]]*\(.*\))/assertThat(\1).isFalse()/g' \
's/assertSame(\(\".*\"\),[[:blank:]]*\(.*\),[[:blank:]]*\(.*\))/assertThat(\3).as(\1).isSameAs(\2)/g' \
's/assertSame([[:blank:]]*\(.*\),[[:blank:]]*\(.*\))/assertThat(\2).isSameAs(\1)/g' \
's/assertNotSame(\(\".*\"\),[[:blank:]]*\(.*\),[[:blank:]]*\(.*\))/assertThat(\3).as(\1).isNotSameAs(\2)/g' \
's/assertNotSame([[:blank:]]*\(.*\),[[:blank:]]*\(.*\))/assertThat(\2).isNotSameAs(\1)/g' \
's/import static org.junit.Assert.assertEquals;/import static org.assertj.core.api.Assertions.assertThat;/g' \
's/import static org.junit.Assert.fail;/import static org.assertj.core.api.Assertions.fail;/g' \
's/import static org.junit.Assert.\*;/import static org.assertj.core.api.Assertions.*;/g'
do
    echo "$EXPRESSION"
    for FILE in $FILES
    do
        sed $EXPRESSION $FILE > /tmp/$$ 2>/dev/null
        if [ $? -eq 0 ]
        then
            chmod 777 $FILE
            cp /tmp/$$ $FILE
            rm /tmp/$$
        else
            echo "- ERRORED !!!"
        fi
    done
done

echo ''
echo 'Now:'
echo '- optimize imports with your IDE to remove unused imports'
echo '- add "import static org.assertj.core.api.Assertions.within;" if you were using JUnit number assertions with delta'

