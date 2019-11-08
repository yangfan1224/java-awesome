package github.com.yangfan1224.awesome.mobpush;

import mob.push.api.MobPushClient;
import mob.push.api.MobPushConfig;
import mob.push.api.exception.ApiException;
import mob.push.api.model.PushWork;
import mob.push.api.utils.AndroidNotifyStyleEnum;
import mob.push.api.utils.PlatEnum;
import mob.push.api.utils.PushTypeEnum;
import mob.push.api.utils.TargetEnum;

public class MobPushClientMain {

    public static void main(String [] args) {
        MobPushConfig.appkey = "2c205239efddc";
        MobPushConfig.appSecret = "aff4d7c72f39583dba1aab6654d0a134";
        MobPushClient mobPushClient = new MobPushClient();

        PushWork push = new PushWork(PlatEnum.android.getCode(),"" , PushTypeEnum.custom.getCode()) //初始化基础信息
                .buildTarget(TargetEnum._4.getCode(), null, null, new String[]{"2b4b17bcbe5b6797708f56f3"}, null, null)  // 设置推送范围
                ;
        try {
            String pushId = mobPushClient.push(push);
            System.out.println(pushId);
        } catch (ApiException e) {
            System.out.println(e.getStatus());
            System.out.println(e.getErrorCode());
            System.out.println(e.getErrorMessage());
            e.printStackTrace();
        }
    }
}
