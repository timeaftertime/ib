:: @关闭紧接着的一条命令的回显
:: echo off 关闭之后所有命令的回显
@echo off
if "%1" == "h" goto begin
:: 隐藏控制台窗口
mshta vbscript:createobject("wscript.shell").run("%~nx0 h",0)(window.close)&&exit
:begin
:: 进入当前文件所在目录
cd /d %~dp0
:: 后台启动
start /b java -classpath .;lib/* cn.milai.ib.InfinityBattle
