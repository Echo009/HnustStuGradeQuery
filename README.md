# HnustStuGradeQuery
## 湖南科技大学学生成绩查询
### 接口分析
- 验证码接口
  - Get http://kdjw.hnust.cn/kdjw/verifycode.servlet
  - 响应体头部
    + Server: Apache-Coyote/1.1
    + Pragma: No-cache
    + Cache-Control: no-cache
    + Expires: Thu, 01 Jan 1970 00:00:00 GMT
    + Set-Cookie: JSESSIONID=B6C643FE4DC36E74A4E95C64E6E82C69; Path=/kdjw
    + Content-Type: image/jpeg
    + Transfer-Encoding: chunked
    + Date: Thu, 29 Jun 2017 02:31:08 GMT
  - 响应体头部重要字段
    + Set-Cookie: JSESSIONID=B6C643FE4DC36E74A4E95C64E6E82C69; Path=/kdjw
    + 那么在后面的会话请求头部的Cookid 字段中必须带上这个sessionId
  - 响应体正文为图片
    + 那么做验证码识别的时候可以直接从输入流中读取数据识别
- 登录接口
  - Post http://kdjw.hnust.cn/kdjw/Logon.do?method=logon
  - 请求头部
    + Accept: text/html, application/xhtml+xml, image/jxr, */*
    + Accept-Encoding: gzip, deflate
    + Accept-Language: en-US, en; q=0.8, zh-Hans-CN; q=0.5, zh-Hans; q=0.3
    + Cache-Control: no-cache
    + Connection: Keep-Alive
    + Content-Length: 80
    + Content-Type: application/x-www-form-urlencoded
    + Cookie: JSESSIONID=B6C643FE4DC36E74A4E95C64E6E82C69
    + Host: kdjw.hnust.cn
    + Referer: http://kdjw.hnust.cn/kdjw/
    + User-Agent: Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 10.0; WOW64; Trident/7.0)
  - 请求体
    + USERNAME: 1405020207
    + PASSWORD: ******
    + RANDOMCODE: xxc1
  - 登录成功响应体
    + 登录成功响应体内容：
      - `<script language='javascript'>window.location.href='http://kdjw.hnust.cn/kdjw/framework/main.jsp';</script>`
    + 失败则返回的是一个html页面
      -包含以下关键内容
        - 验证码错误 
          - `<span style="color: red;" id="errorinfo">验证码错误!!</span>`
        - 用户名或者密码错误
          - `<span style="color: red;" id="errorinfo">该帐号不存在或密码错误,请联系管理员!</span>`
- 成绩查询接口
  - Post http://kdjw.hnust.cn/kdjw/xszqcjglAction.do?method=queryxscj	
  - 请求头部
    + Accept: text/html, application/xhtml+xml, image/jxr, */*
    + Accept-Encoding: gzip, deflate
    + Accept-Language: en-US, en; q=0.8, zh-Hans-CN; q=0.5, zh-Hans; q=0.3
    + Cache-Control: no-cache
    + Connection: Keep-Alive
    + Content-Length: 42
    + Content-Type: application/x-www-form-urlencoded
    + Cookie: JSESSIONID=B6C643FE4DC36E74A4E95C64E6E82C69
    + Host: kdjw.hnust.cn
    + Referer: http://kdjw.hnust.cn/kdjw/jiaowu/cjgl/xszq/query_xscj.jsp
    + User-Agent: Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 10.0; WOW64; Trident/7.0)
  - 请求体
    + kcmc: 
    + kcxz: 
    + kksj: 2016-2017-2
    + ok: 
    + xsfs: qbcj
  - 在测试过程中发现 
    + 直接向http://kdjw.hnust.cn/kdjw/xszqcjglAction.do?method=queryxscj 发送Get请求就返回了包含所有成绩的HTML页面
### 模拟登录流程
  - 访问验证码接口，获得服务器返回的SessionID ，并从输入流中获取图片，识别图片中的验证码
  - 向登录接口发送Post 请求
    + 将Cookie字段值 设置为获取到的SessionID内容
    + 传递请求体参数 
      - USERNAME
      - PASSWORD
      - RANDOMCODE
    + 登录成功返回的响应体正文仅包含一段JavaScript脚本
      - 那么可以直接通过读取输入流的内容，通过判断其长度来判断是否登录成功
    + 登录失败则会返回一个HTML页面 
      - 验证码错误
      - 用户名或者密码错误
      - 这里需要判断通过读取HTML页面的内容判断一下属于哪种错误，但是可以采用更加粗暴一点的策略，不成功则重新尝试，最多重试五次，如果五次不成功的话就基本上可以判定为是用户名或者密码错误了。
      - 测试过验证码识别模块，2000个用例，识别成功率为0.677，按这个概率粗略统计，连续五次因验证码错误而导致登录失败的概率为 0.333^5 = 0.0041 <1%
    
### 获取成绩内容

  - 直接向http://kdjw.hnust.cn/kdjw/xszqcjglAction.do?method=queryxscj 发送Get请求
  - 解析HTML文档，从中提取有效信息 ，封装成Json格式返回。
  
