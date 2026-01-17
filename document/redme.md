一、推荐方式（生产首选）：OpenSSL 生成 PKCS8 私钥
1️⃣ 生成 RSA 私钥（PKCS1）
openssl genrsa -out rsa_private_pkcs1.pem 2048
说明：
•	2048 位是当前主流安全基线
•	输出为 PKCS1 格式
________________________________________
2️⃣ 转换为 PKCS8（无密码，JWT 常用）
openssl pkcs8 \
-topk8 \
-inform PEM \
-outform PEM \
-nocrypt \
-in rsa_private_pkcs1.pem \
-out rsa_private_pkcs8.pem
得到：
-----BEGIN PRIVATE KEY-----
MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBK...
-----END PRIVATE KEY-----
✔ 这就是你 JWT RS256 所需的私钥格式
________________________________________
3️⃣ 生成公钥（与你的 JwtUtil.parse 匹配）
openssl rsa \
-in rsa_private_pkcs8.pem \
-pubout \
-out rsa_public.pem
输出：
-----BEGIN PUBLIC KEY-----
MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8A...
-----END PUBLIC KEY-----
________________________________________
二、Java 程序生成（可用于工具 / 测试）