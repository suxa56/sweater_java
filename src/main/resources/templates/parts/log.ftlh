<#macro login path isRegisterForm>
    <form action="${path}" method="post" class="mt-3">
        <div class="mb-3 col-4">
            <label for="loginName" class="form-label"> User name </label>
            <input class="form-control ${(usernameError??)?string('is-invalid', '')}"
                   id="loginName" type="text" name="username" placeholder="Username"
            value="<#if user??>${user.username}</#if>"/>
            <#if usernameError??>
                <div class="invalid-feedback">
                    ${usernameError}
                </div>
            </#if>
        </div>
        <div class="mb-3 col-4">
            <label for="loginPass" class="form-label"> Password </label>
            <input class="form-control ${(passwordError??)?string('is-invalid', '')}" id="loginPass" type="password" name="password" placeholder="Password"/>
            <#if passwordError??>
                <div class="invalid-feedback">
                    ${passwordError}
                </div>
            </#if>
        </div>
        <#if isRegisterForm>
            <div class="mb-3 col-4">
                <label for="retypePassword" class="form-label"> Confirm password </label>
                <input class="form-control ${(passwordError??)?string('is-invalid', '')}" id="retypePassword"
                       type="password" name="password2" placeholder="Retype password"/>
                <#if password2Error??>
                    <div class="invalid-feedback">
                        ${password2Error}
                    </div>
                </#if>
            </div>
            <div class="mb-3 col-4">
                <label for="loginEmail" class="form-label"> Email </label>
                <input class="form-control ${(emailError??)?string('is-invalid', '')}"
                       id="loginEmail" type="email" name="email"
                       placeholder="example@example.com"
                       value="<#if user??>${user.email}</#if>"/>
                <#if emailError??>
                    <div class="invalid-feedback">
                        ${emailError}
                    </div>
                </#if>
            </div>
            <div class="col-sm-6">
                <div class="g-recaptcha" data-sitekey="6LcZeU8aAAAAAF5GMLXujm2mM2NRhkBunrPd4UKY"></div>
                <#if captchaError??>
                    <div class="alert alert-danger">
                        ${captchaError}
                    </div>
                </#if>
            </div>
        </#if>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <#if !isRegisterForm>
            <div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
                <div class="btn-group">
                    <button type="submit" class="btn btn-primary me-2">Sign in</button>
                </div>
                <div class="btn-group">
                    <a href="/registration" class="btn btn-success">Sign up</a>
                </div>
            </div>
        <#else >
            <button type="submit" class="btn btn-success">Sign up</button>
        </#if>
    </form>
</#macro>

<#macro logout>
    <form action="/logout" method="post" class="ms-2 col-1">
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <button type="submit" class="btn btn-primary"> Sign out</button>
    </form>
</#macro>