package io.github.zhiweicoding.csw.api;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.HMac;
import cn.hutool.crypto.digest.HmacAlgorithm;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Signature=fFG%2BusugjKwssVzaPH0FXZPkSWY%3D
 * &AccessKeyId=testid
 * &Action=LookupEvents
 * &Format=JSON&RegionId=cn-hangzhou
 * &SignatureMethod=HMAC-SHA1
 * &SignatureNonce=08d80560-0f4f-11eb-8cbb-0972fab51c81
 * &SignatureVersion=1.0
 * &Timestamp=2020-10-16T01%3A29%3A29Z
 * &Version=2020-07-06
 * <p>
 * Signature = Base64( HMAC-SHA1( AccessKey Secret, UTF-8-Encoding-Of(StringToSign)) )
 * <p>
 * SignatureVersion=1.0&SignatureMethod=HMAC-SHA1&Signature=CT9X0VtwR86fNWSnsc6v8YGOjuE%3D&SignatureNonce=3ee8c1b8-83d3-44af-a94f-4e0ad82fd6cf
 * StringToSign=
 * HTTPMethod + “&” +
 * percentEncode(“/”) + ”&” +
 * percentEncode(CanonicalizedQueryString)
 *
 * @Created by zhiwei on 2022/5/20.
 */
public class SearchControllerTest {


    public static void main(String[] args) throws Exception {
    }

}