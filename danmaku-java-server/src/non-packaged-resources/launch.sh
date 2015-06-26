#/usr/bin
PROJECT_HOME=.
MAIN_CLASS=com.danmaku.server.DanmakuServer
LIB_OPTS="$PROJECT_HOME:$PROJECT_HOME/lib:$PROJECT_HOME/lib/*:$PROJECT_HOME/classes"
java -cp $LIB_OPTS $MAIN_CLASS