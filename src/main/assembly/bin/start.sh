#!/bin/bash
JAVA_CMD=$JAVA_HOME/bin/java
if [ ! -d "$JAVA_HOME" ]; then
    echo ---------------------------------------------------
    echo WARN: JAVA_HOME environment variable is not set. 
    echo ---------------------------------------------------
    JAVA_CMD=java
fi
MAIN_CLASS=com.brotherbin.antbench.Launcher
MAIN_ARGS=$1
CURR_DIR=`pwd`
cd ..
ANT_HOME=`pwd`
cd $CURR_DIR
JAVA_OPT="-server -Xms2G -Xmx2G -XX:MaxPermSize=64M  -XX:+AggressiveOpts -XX:MaxDirectMemorySize=2G -DANT_HOME=$ANT_HOME -Dcom.sun.management.jmxremote=true -Dcom.sun.management.jmxremote.port=1970 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false"
CLASS_PATH="$ANT_HOME/conf:$ANT_HOME/lib/*"
$JAVA_CMD $JAVA_OPT -cp $CLASS_PATH $MAIN_CLASS $MAIN_ARGS