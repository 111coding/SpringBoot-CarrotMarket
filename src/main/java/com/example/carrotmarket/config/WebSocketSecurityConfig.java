package com.example.carrotmarket.config;

import com.example.carrotmarket.enums.UserRoleType;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

@Configuration
public class WebSocketSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {

    //  https://godekdls.github.io/Spring%20Security/integrations/
    //  (1) destination이 없는 모든 메세지는 (i.e. MESSAGE, SUBSCRIBE를 제외한 모든 메세지 타입) 사용자를 인증해야 한다.
    //  (2) 누구든지 /user/queue/errors를 구독할 수 있다.
    //  (3) “/app/”으로 시작하는 destination이 있는 모든 메세지는 ROLE_USER 권한이 필요하다.
    //  (4) “/user/”나 “/topic/friends/”로 시작하는 모든 SUBSCRIBE 타입 메세지는 ROLE_USER 권한이 필요하다.
    //  (5) 다른 MESSAGE, SUBSCRIBE 타입 메세지는 모두 거절한다. 6번이 있어서 이 설정은 없어도 되지만, 특정 메세지 타입을 매칭하는 방법을 보여준다.
    //  (6) 그 외 모든 메세지는 거절한다. 특정 메세지를 누락하지 않도록 이렇게 막아두는 게 좋다.
    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
        messages
                .nullDestMatcher().authenticated() // (1) => /ws auth
                .simpSubscribeDestMatchers("/user/queue/errors").permitAll() // (2)
                .simpMessageDestMatchers("/chat-socket/chat.send").hasRole(UserRoleType.USER.getValue()) // (3)
                 // role should not start with 'ROLE_' since it is automatically inserted. Got 'ROLE_USER'
                // prefix : ROLE_
                // handshake 일어날 때의 권한으로 계속 인증
                .simpSubscribeDestMatchers("/user/queue/pub").hasRole(UserRoleType.USER.getValue()) // (4)
//                .simpTypeMatchers(SimpMessageType.MESSAGE, SimpMessageType.SUBSCRIBE).denyAll() // (5)
                .anyMessage().denyAll(); // (6)
    }

    @Override
    protected boolean sameOriginDisabled() {
        return true;
    }
}