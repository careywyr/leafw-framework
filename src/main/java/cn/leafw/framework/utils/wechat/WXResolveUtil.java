package cn.leafw.framework.utils.wechat;

import cn.leafw.framework.dto.WechatDecrypDTO;
import cn.leafw.framework.exception.BusinessException;
import cn.leafw.framework.utils.SpringContext;
import cn.leafw.framework.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.AlgorithmParameterSpec;
import java.util.HashMap;
import java.util.Map;

/**
 * @author carey
 */
@Slf4j
public class WXResolveUtil {

	private static final String OPEN_ID_URL = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";

	private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";

	private static final RestTemplate restTemplate = SpringContext.getBean("restTemplate");

	public static String decrypData(WechatDecrypDTO wechatDecrypDTO, String type, String appId, String appSecret) {
		String sessionkey = wechatDecrypDTO.getSessionKey();
		if (StringUtils.isEmpty(wechatDecrypDTO.getSessionKey())) {
			sessionkey = getSessionKey(wechatDecrypDTO.getCode(), appId, appSecret);
			if (StringUtils.isEmpty(sessionkey)) {
				throw new RuntimeException("sessionKey为空");
			}
		}
		byte[] encrypData = Base64Utils.decode(wechatDecrypDTO.getEncrypdata().getBytes());
		byte[] ivData = Base64Utils.decode(wechatDecrypDTO.getIvdata().getBytes());
		byte[] sessionKey = Base64Utils.decode(sessionkey.getBytes());
		String str = "";
		try {
			str = decrypt(sessionKey, ivData, encrypData);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		log.info(str);
		if (StringUtils.isNotEmpty(str) && "phoneNumber".equals(type)) {
			Map<String, String> telObj = JSONObject.parseObject(str, Map.class);
			String tel = telObj.get("phoneNumber");
			return tel;
		}
		if (StringUtils.isNotEmpty(str) && "loginInfo".equals(type)) {
			Map<String, Object> Obj = JSONObject.parseObject(str, Map.class);
			String unionId = (String) Obj.get("unionId");
			return unionId;
		}
		return null;
	}

	public static String decrypt(byte[] key, byte[] iv, byte[] encData) throws Exception {
		AlgorithmParameterSpec ivSpec = new IvParameterSpec(iv);
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
		cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
		//解析解密后的字符串
		return new String(cipher.doFinal(encData), "UTF-8");
	}

	public static String getSessionKey(String code, String appId, String appSecret) {
		// 获取openid
		String openIdUrl = getOpenIdUrl(code, appId, appSecret);
		String res = restTemplate.getForObject(openIdUrl, String.class);
		log.info("openIdUrl获取的result为： {}", res);
		JSONObject jsonObj = JSONObject.parseObject(res);
		if (null == jsonObj || StringUtils.isEmpty(jsonObj.getString("openid")) || StringUtils.isBlank(jsonObj.getString("openid"))) {
			log.info("登录失败，原因是: {}", jsonObj);
			throw BusinessException.of("微信登陆失败,原因："+jsonObj.getString("errmsg"));
		}
		return jsonObj.getString("session_key");
	}

	public static Map<String, String> getSessionKeyAndOpenId(String code, String appId, String appSecret) {
		// 获取openid
		String openIdUrl = getOpenIdUrl(code, appId, appSecret);
		String res = restTemplate.getForObject(openIdUrl, String.class);
		log.info("openIdUrl获取的result为： {}", res);
		JSONObject jsonObj = JSONObject.parseObject(res);
		String openid = jsonObj.getString("openid");
		if (null == jsonObj || StringUtils.isEmpty(openid) || StringUtils.isBlank(openid)) {
			log.info("登录失败，原因是: {}", jsonObj);
			throw BusinessException.of("微信登陆失败,原因："+jsonObj.getString("errmsg"));
		}
		String sessionKey = jsonObj.getString("session_key");
		Map<String, String> userLoginMap = new HashMap<>();
		userLoginMap.put("openId", openid);
		userLoginMap.put("sessionKey", sessionKey);
		return userLoginMap;
	}

	public static String getOpenId(String code, String appId, String appSecret) {
		// 获取openid
		String openIdUrl = getOpenIdUrl(code, appId, appSecret);
		String res = restTemplate.getForObject(openIdUrl, String.class);
		log.info("openIdUrl获取的result为： {}", res);
		JSONObject jsonObj = JSONObject.parseObject(res);
		if (null == jsonObj || StringUtils.isBlank(jsonObj.getString("openid"))) {
			log.info("登录失败，原因是: {}", jsonObj);
			throw BusinessException.of("微信登陆失败,原因："+jsonObj.getString("errmsg"));
		}
		return jsonObj.getString("openid");
	}

	/**
	 * 通过自定义工具类组合出小程序需要的登录凭证 code
	 *
	 * @param code
	 * @return
	 */
	private static String getOpenIdUrl(String code, String appId, String appSecret) {
		return String.format(OPEN_ID_URL, appId, appSecret, code);
	}

	/**
	 * 获取openId unionid
	 * pc端登陆需要的
	 *
	 * @param appid     appid
	 * @param appSecret appsecret
	 * @param code      校验码
	 * @return
	 */
	public static String getAccessToken(String appid, String appSecret, String code) {
		log.info("---参数为： appId: {}, appSecret: {}, code: {} ---", appid, appSecret, code);
		String format = String.format(ACCESS_TOKEN_URL, appid, appSecret, code);
		log.info("---拼装的url为: {}---", format);
		String res = restTemplate.getForObject(format, String.class);
		log.info("---得到的结果为： {}---", res);
		return res;
	}

}
