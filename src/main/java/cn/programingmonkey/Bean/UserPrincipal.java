package cn.programingmonkey.Bean;

import javax.security.auth.Subject;
import java.security.Principal;

/**
 * Created by cai on 2017/2/15.
 */
public class UserPrincipal implements Principal {



    public String getName() {
        return null;
    }

    public boolean implies(Subject subject) {
        return false;
    }
}
