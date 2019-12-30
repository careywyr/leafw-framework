package cn.leafw.framework.dto;

import lombok.Data;

/**
 * @author CareyWYR
 * @description
 */
@Data
public class WechatDecrypDTO {

   private String code;
   private String encrypdata;
   private String ivdata;
   private String sessionKey;
}
