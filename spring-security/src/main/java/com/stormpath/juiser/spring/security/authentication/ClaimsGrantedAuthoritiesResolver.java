package com.stormpath.juiser.spring.security.authentication;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.function.Function;

/**
 * @since 0.1.0
 */
public class ClaimsGrantedAuthoritiesResolver implements Function<Claims, Collection<? extends GrantedAuthority>> {

    private Function<Claims, Collection<String>> authorityStringsResolver;

    public ClaimsGrantedAuthoritiesResolver(Function<Claims, Collection<String>> authorityStringsResolver) {
        Assert.notNull(authorityStringsResolver, "authorityStringsResolver cannot be null.");
        this.authorityStringsResolver = authorityStringsResolver;
    }

    @Override
    public Collection<? extends GrantedAuthority> apply(Claims claims) {

        Collection<String> authorityStrings = authorityStringsResolver.apply(claims);

        if (CollectionUtils.isEmpty(authorityStrings)) {
            return Collections.emptyList();
        }

        return AuthorityUtils.createAuthorityList(authorityStrings.toArray(new String[authorityStrings.size()]));
    }
}
