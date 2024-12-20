#!/bin/bash

# sh convert_to_agora.sh

unamestr=`uname`

if [[ "$unamestr" = MINGW* || "$unamestr" = "Linux" ]]; then
    	SED="sed"
elif [ "$unamestr" = "Darwin" ]; then
    	SED="gsed"
else
    	echo "only Linux, MingW or Mac OS can be supported" &
    	exit 1
fi



echo "----------------- Start convert to agora -----------------"
# enter root dir
cd ..
echo "当前路径：$(pwd)"
# 清空本地改动,切换到agora分支
git checkout -f ; git clean -fd ; git checkout dev; git branch -D dev-agora; git checkout -b dev-agora

echo "current branch :"
git branch

python3 scripts/change_package_name.py ./

python3 scripts/rename_file_and_update_content.py ./ --replace-content

#处理readme
cp document/ChatroomUIKit.md README.md

$SED -i 's/\*English | \[中文\](ChatroomUIKit_zh.md)\*/\*English | \[中文\](document\/ChatroomUIKit_zh.md)\*/' README.md
$SED -i 's/\.\.\//.\//g' README.md

git add . ; git commit -m "convert to agora"

echo "----------------- Finish convert to agora -----------------"





