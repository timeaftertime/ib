# 进入当前文件所在目录
cd $(dirname $(readlink -f "$0"))
# 启动
java -classpath .:lib/* cn.milai.ib.InfinityBattle &
