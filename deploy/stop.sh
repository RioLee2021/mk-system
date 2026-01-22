#!/bin/bash
TPID_FILE="tpid"

# 检查 tpid 文件是否存在
if [ ! -f $TPID_FILE ]; then
  echo "tpid file does not exist. Application is not running or already stopped."
  exit 1
fi

# 读取进程 ID
tpid=$(cat $TPID_FILE)

# 检查是否读取到有效的 PID
if [ -z "$tpid" ]; then
  echo "No PID found in tpid file. Application may not be running."
  exit 1
fi

# 检查进程是否在运行
if ps -p $tpid > /dev/null 2>&1; then
  echo "Stopping application (PID: $tpid) ..."
  kill -15 $tpid

  # 等待进程完全停止
  sleep 5

  # 再次检查进程是否已经停止
  if ps -p $tpid > /dev/null 2>&1; then
    echo "Force killing application (PID: $tpid) ..."
    kill -9 $tpid
  else
    echo "Application stopped successfully."
  fi

  # 删除 tpid 文件
  rm -f $TPID_FILE
  echo "Stop Success!"
else
  echo "No process found with PID: $tpid. It may have already been stopped."
  rm -f $TPID_FILE
fi
