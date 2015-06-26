#/usr/bin
PROJECT_HOME=.
MAIN_CLASS=com.danmaku.main.Danmaku
LIB_OPTS="$PROJECT_HOME;$PROJECT_HOME/lib;$PROJECT_HOME/lib/*;$PROJECT_HOME/classes;"
$JAVA_HOME/bin/java -cp $LIB_OPTS $MAIN_CLASS