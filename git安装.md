# git ʹ�ý̳�

## ����

### win7ϵͳ��

[https://git-for-windows.github.io/](https://git-for-windows.github.io/)

### macϵͳ

macϵͳ���Ƽ���װApple��˾��Xcode��Xcode������Git�����°��Xcode�Ѿ�Ĭ�ϰ�װ����git����ɰ�װ֮�󣬾Ϳ������ն�ʹ��git�������й��ߡ�


## ��װ�̳�

win7�������½̳̰�װ

[http://blog.csdn.net/fengye_yulu/article/details/52116146](http://blog.csdn.net/fengye_yulu/article/details/52116146)

���ݽ̳̣�һ������װ����


## ����


## git����
��1��ע��һ��gitlab�˻�����http://10.1.108.137/ ��ע���˺�

��2�������˺���Ϣ

````
git config --global user.name yourname //�û�����д�������
git config --global user.email youreamil@gmail.com //�û�������д�������

````
��3������ssh key

````
ssh-keygen -t rsa -C "youreamil@gmail.com"

````
�������ϴ���󣬽���ȥ���ǻس��������������루���������

��4��ssh key���õ�github

````
cd ~/.ssh
cat id_rsa.pub //�鿴ssh key

````
����ssh key�󣬵�½gitlab��վ��������Ͻ����ͷ���ڵ�������ѡSettings��Ȼ��ѡSSH keys������������Ҳ��Add SSH key��ճ��ssh key�������ƴ���

## git����ssh key�����ؽ�����ssh key������

�����ͬƽ̨��Ҫ��ͬ��email���ɵ�ssh key,���Բο� [git����ssh key�����ؽ�����ssh key������](http://riny.net/2014/git-ssh-key/)���ܽ����ϸ�����Ų����Ϳ��ԡ�

�������Ҫ����ƽ̨������������ssh key���ֱ𵽸���ƽ̨ȥAdd SSH key���ɡ�����gitlab��github�õľ���ͬһ��ssh keyͨ�š�

## gitlab����github�ϴ�����Ŀ
gitlab����github�ϴ�����Ŀ������һģһ�����������gitlab



1��gitlab�ϵĴ�����Ŀ��ť������Ŀ

![https://raw.githubusercontent.com/blueIce525/myblog/master/images/git1.png](https://raw.githubusercontent.com/blueIce525/myblog/master/images/git1.png)

2�����봴����Ŀҳ��

![https://raw.githubusercontent.com/blueIce525/myblog/master/images/git2.png](https://raw.githubusercontent.com/blueIce525/myblog/master/images/git2.png)

3����д��Ŀ��Ϣ�����������ť

![https://raw.githubusercontent.com/blueIce525/myblog/master/images/git3.png](https://raw.githubusercontent.com/blueIce525/myblog/master/images/git3.png)

4��������Ŀҳ�棬��3�ֲ������ڱ��ص�git�����ϣ�������Ŀҳ����������������ѡ���һ�ִ���������clone���ύ��Ŀ

![https://raw.githubusercontent.com/blueIce525/myblog/master/images/git4.png](https://raw.githubusercontent.com/blueIce525/myblog/master/images/git4.png)

5�����ϲ�����ˢ����Ŀҳ�棬���ɿ�����Ŀ�Ѿ�����

![https://raw.githubusercontent.com/blueIce525/myblog/master/images/git5.png](https://raw.githubusercontent.com/blueIce525/myblog/master/images/git5.png)

**ע�⣺��ĿҪ���һ��.gitignore������һЩ�ļ����ļ��в��ύ���ҳ����Ե��ļ���**

```
node_modules/
.DS_Store
.idea/
.cache
.sass-cache
tmp/
bower_components/
build/
dest/

```

GitHub�Ѿ�Ϊ����׼���˸��������ļ���ֻ��Ҫ���һ�¾Ϳ���ʹ���ˡ����������ļ�����ֱ�����������[https://github.com/github/gitignore](https://github.com/github/gitignore)


## git�ճ��������������
**���ڳ�ѧ�ߣ����������³��õ������clone���ύ��������tag,�����ٽ���ʹ�ø��ӵĲ������һЩ**��

1���Ѵ���clone�����أ�git clone �ֿ��ַ

2���鿴��֧:  git branch -a��-a����鿴���еķ�֧������Զ�̵ģ�

3���л���Ҫ�޸ĵķ�֧: git checkout ��֧��a ��a��֧��Ϊ���������base��֧��

4���ڵ�ǰ�ķ�֧���½�һ����֧���л����½���֧:  git checkout -b �·�֧��b �� git checkout -b �·�֧��b �ȼ����ȴ�����b��֧Ȼ����checkout��b��֧��������ĺ��� ��

5���鿴�޸Ĺ��ļ���״̬�� git status

6���޸��ļ�������ļ���������:  git add  -A

7�������������ļ��ύ�����ط�֧��:  git commit -m "�ύ������Ϣ"

8���л�����֧��a:  git checkout ��֪��a ���л���base��֧��

9����Զ�ֿ̲������pull�����أ� git pull ����Ϊ������Ҳ����push��base��֧�ˣ�ȫд�������� git pull origin ��֪��a��

10�����·�֧b�ϲ�����֧a:  git merge ��֧��b �����޸ĵķ�֧�ϲ���base��֧��

11������֧a���͵�Զ�̣� git  push -u origin ��֪��a �����޸ĺ��base��֧�ύ��

12����ǩ��git tag tag�����ƣ�����publish/2.0.3��

13���鿴���еı�ǩ�� git tag

14�����ͱ�ǩ��git push origin tag�����ƣ�����publish/2.0.3��


## ��������

���õ��������£�
### Զ�ֿ̲��������
git clone [url]   clone�ֿ�,�ӷ������Ͻ������������

git remote -v  �鿴Զ�ֿ̲�

git remote add [name] [url] ���Զ�ֿ̲�

git pull [remoteName] [localBranchName] ��ȡԶ�ֿ̲�

git push [remoteName] [localBranchName]  ����Զ�ֿ̲�

git fetch �൱���Ǵ�Զ�̻�ȡ���°汾�����أ������Զ�merge

### �鿴��Ϣ

git branch -a �鿴���еķ�֧

git status �鿴��ǰ״̬

git diff �鿴��δ�ݴ�ĸ���

git tag �鿴tag

git log ����commit����־

git reflog ��ʾ��ǰ��֧����������ύ


### ��֧����

git checkout -b [name]  �����·�֧�������л����·�֧

git checkout [name]  �л���֧

git branch -d [name] ɾ�������Ѿ��ϲ��ķ�֧

git branch -D [name] ǿ��ɾ�����ط�֧����ʹδ�ϲ�

git push origin [name]   ���ͷ�֧��Զ��

git push origin :[name] ɾ��Զ�̷�֧

git merge [name] �ϲ�ĳ��֧����ǰ��֧

git merge --no-ff -m "commit����" [name]  �ϲ���֧ʱ������--no-ff�����Ϳ�������ͨģʽ�ϲ����ϲ������ʷ�з�֧���ܿ��������������ϲ�

### ɾ���ļ�

git rm [filename] ������stage��ɾ����ͬʱɾ�������ļ�

### �ύ����

git add [file name] ���һ���ļ����ݴ���

git add .  �������޸Ĺ��Ĺ����ļ��ύ�ݴ���

git commit ���ļ���stage�ύ��branch

git commit -a ���޸ĵ��ļ����ύ��stage,Ȼ���ٴ�stage�ύ��branch

###  �����ͻ

git merge [branch] ���� git pull ������ͻʱ�������л��г���ͻ���ļ����ֶ������ͻ�����ύ��git statusҲ���Ը������ǳ�ͻ���ļ��������������еĳ�ͻ�������û��


### �����ع�

git checkout -- [file] �������������޸�

git reset HEAD [file] �ָ��ݴ�����ָ���ļ���������

git log ����commit����־

git reset --hard ��commit_id�ݳ�������δ�ύ��Զ�̵ĸĶ�

git push -f origin ��master/branch��ǿ���ύ���ϵĳ���

git revert HEAD  �������һ�ε��ύ������Զ���ύ��Ҫ�ѵ�ǰ�����ύһ��git push origin��master/branch��

git revert [commit_id��һ�����д����ͻ����Ҫ��ϲ����룬�ϲ�����ֻ��Ҫ�ѵ�ǰ�Ĵ���ȫ��ȥ����Ҳ����HEAD��ǵĴ���


### �汾(tag)�����������
git tag �鿴�汾

git tag [name] �����汾

git push origin [name] ����Զ�̰汾(���ذ汾push��Զ��)

git tag -d [name] ɾ���汾

git push origin :refs/tags/[name] ɾ��Զ�̰汾

git tag -r �鿴Զ�̰汾

git pull origin --tags �ϲ�Զ�ֿ̲��tag������

git push origin --tags �ϴ�����tag��Զ�ֿ̲�


### ����
git stash push ���ļ���push��һ����ʱ�ռ���

git stash pop ���ļ�����ʱ�ռ�pop����


## ʹ�ÿͻ��˵�ͬѧ�ο��������

[http://blog.csdn.net/allenjay11/article/details/51941829](http://blog.csdn.net/allenjay11/article/details/51941829)



## ��¼��

[Git�Ĺٷ���վ](https://git-scm.com/book/zh/v1/%E8%B5%B7%E6%AD%A5)

[git�̳�](http://www.liaoxuefeng.com/wiki/0013739516305929606dd18361248578c67b8067c8c017b000)

[git����ָ��](http://www.bootcss.com/p/git-guide/)

[���� Git �����嵥](http://www.bootcss.com/p/git-guide/)

[Git ���������ȫ](http://blog.csdn.net/dengsilinming/article/details/8000622)