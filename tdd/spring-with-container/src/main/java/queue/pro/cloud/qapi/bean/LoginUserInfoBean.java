package queue.pro.cloud.qapi.bean;

import lombok.Data;


public record LoginUserInfoBean(String loginId, String email, String... roles) {


}
