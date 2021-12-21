<html>
<#-- @ftlvariable name="data" type="<html>ru.instamart.api.common.HttpRequestAttachmentCustom" -->
<head>
    <meta http-equiv="content-type" content="text/html; charset = UTF-8">
    <script src="https://yastatic.net/jquery/2.2.3/jquery.min.js" crossorigin="anonymous"></script>

    <link href="https://yastatic.net/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">
    <script src="https://yastatic.net/bootstrap/3.3.6/js/bootstrap.min.js" crossorigin="anonymous"></script>

    <link type="text/css" href="https://yandex.st/highlightjs/8.0/styles/github.min.css" rel="stylesheet"/>
    <script type="text/javascript" src="https://yandex.st/highlightjs/8.0/highlight.min.js"></script>
    <script type="text/javascript" src="https://yandex.st/highlightjs/8.0/languages/bash.min.js"></script>
    <script type="text/javascript" src="https://yandex.st/highlightjs/8.0/languages/json.min.js"></script>
    <script type="text/javascript" src="https://yandex.st/highlightjs/8.0/languages/xml.min.js"></script>
    <script type="text/javascript">hljs.initHighlightingOnLoad();</script>

    <style>
        pre {
            white-space: pre-wrap;
        }
    </style>
</head>
<body>
<div>
    <h4>Request URL</h4>
    <pre><code><#if data.method??>${data.method}<#else>GET</#if>: <#if data.url??>${data.url}<#else>Unknown</#if></code></pre>
</div>

<#if data.body??>
<h4>Body</h4>
<div>
    <pre><code>${data.body}</code></pre>
</div>
</#if>

<#if (data.headers)?has_content>
<h4>Headers</h4>
<div>
    <#list data.headers as name, value>
        <div>
            <pre><code><b>${name}</b>: ${value}</code></pre>
        </div>
    </#list>
</div>
</#if>


<#if (data.cookies)?has_content>
<h4>Cookies</h4>
<div>
    <#list data.cookies as name, value>
        <div>
            <pre><code><b>${name}</b>: ${value}</code></pre>
        </div>
    </#list>
</div>
</#if>


<#if (data.formParam)?has_content>
<h4>Data params</h4>
<div>
    <#list data.formParam as name, value>
        <div>
        <#if value??>
            <#if value?is_collection || value?is_indexable>
                <#list value as x>
                    <pre><code><b>${name}</b>: ${x}</code></pre>
                </#list>
            <#else>
                <pre><code><b>${name}</b>: ${value}</code></pre>
            </#if>
        </#if>
        </div>
    </#list>
</div>
</#if>

<#if (data.multiPartParam)?has_content>
<h4>Multi part</h4>
<div>
    <#list data.multiPartParam as values>
        <div>
            <pre><code>${values}</code></pre>
        </div>
    </#list>
</div>
</#if>

<#if data.curl??>
<h4>Curl</h4>
<div>
    <pre><code>${data.curl}</code></pre>
</div>
</#if>
</body>
</html>