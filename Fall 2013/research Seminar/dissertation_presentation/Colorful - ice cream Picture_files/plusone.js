var gapi=window.gapi=window.gapi||{};gapi._bs=new Date().getTime();(function(){var aa=encodeURIComponent,l=window,ba=Object,q=document,da=Array,ea=parseInt,r=String,fa=decodeURIComponent;function ga(a,b){return a.type=b}
var ha="appendChild",s="push",t="test",ia="shift",ja="exec",ka="width",v="replace",la="getElementById",ma="concat",na="charAt",oa="JSON",x="indexOf",pa="nodeName",qa="match",ra="readyState",z="createElement",A="setAttribute",sa="type",ta="bind",ua="getTime",va="getElementsByTagName",B="substr",C="toString",D="length",E="prototype",F="split",G="location",H="style",wa="removeChild",I="call",J="getAttribute",xa="protocol",K="charCodeAt",L="href",M="substring",ya="documentMode",za="action",N="apply",
Aa="attributes",O="parentNode",Ba="update",Ca="height",P="join",Da="toLowerCase",Ea=function(a,b,c){return a[I][N](a[ta],arguments)},Fa=function(a,b,c){if(!a)throw Error();if(2<arguments[D]){var d=da[E].slice[I](arguments,2);return function(){var c=da[E].slice[I](arguments);da[E].unshift[N](c,d);return a[N](b,c)}}return function(){return a[N](b,arguments)}},Ga=function(a,b,c){Ga=Function[E][ta]&&-1!=Function[E][ta][C]()[x]("native code")?Ea:Fa;return Ga[N](null,arguments)};
Function[E].bind=Function[E][ta]||function(a,b){if(1<arguments[D]){var c=da[E].slice[I](arguments,1);c.unshift(this,a);return Ga[N](null,c)}return Ga(this,a)};var Q=l,R=q,Ha=Q[G],Ia=function(){},Ja=/\[native code\]/,S=function(a,b,c){return a[b]=a[b]||c},Ka=function(a){for(var b=0;b<this[D];b++)if(this[b]===a)return b;return-1},La=function(a){a=a.sort();for(var b=[],c=void 0,d=0;d<a[D];d++){var e=a[d];e!=c&&b[s](e);c=e}return b},Ma=/&/g,Na=/</g,Oa=/>/g,Pa=/"/g,Qa=/'/g,Ra=function(a){return r(a)[v](Ma,"&amp;")[v](Na,"&lt;")[v](Oa,"&gt;")[v](Pa,"&quot;")[v](Qa,"&#39;")},T=function(){var a;if((a=ba.create)&&Ja[t](a))a=a(null);else{a={};for(var b in a)a[b]=
void 0}return a},U=function(a,b){return ba[E].hasOwnProperty[I](a,b)},Sa=function(a){if(Ja[t](ba.keys))return ba.keys(a);var b=[],c;for(c in a)U(a,c)&&b[s](c);return b},V=function(a,b){a=a||{};for(var c in a)U(a,c)&&(b[c]=a[c])},Ta=function(a){return function(){Q.setTimeout(a,0)}},Ua=function(a,b){if(!a)throw Error(b||"");},W=S(Q,"gapi",{});var X=function(a,b,c){var d=RegExp("([#].*&|[#])"+b+"=([^&#]*)","g");b=RegExp("([?#].*&|[?#])"+b+"=([^&#]*)","g");if(a=a&&(d[ja](a)||b[ja](a)))try{c=fa(a[2])}catch(e){}return c},Va=/^([^?#]*)(\?([^#]*))?(\#(.*))?$/,Wa=function(a){a=a[qa](Va);var b=T();b.R=a[1];b.g=a[3]?[a[3]]:[];b.l=a[5]?[a[5]]:[];return b},Xa=function(a){return a.R+(0<a.g[D]?"?"+a.g[P]("&"):"")+(0<a.l[D]?"#"+a.l[P]("&"):"")},Ya=function(a,b){var c=[];if(a)for(var d in a)if(U(a,d)&&null!=a[d]){var e=b?b(a[d]):a[d];c[s](aa(d)+"="+
aa(e))}return c},Za=function(a,b,c,d){a=Wa(a);a.g[s][N](a.g,Ya(b,d));a.l[s][N](a.l,Ya(c,d));return Xa(a)},$a=function(a,b){var c="";2E3<b[D]&&(c=b[M](2E3),b=b[M](0,2E3));var d=a[z]("div"),e=a[z]("a");e.href=b;d[ha](e);d.innerHTML=d.innerHTML;b=r(d.firstChild[L]);d[O]&&d[O][wa](d);return b+c},ab=/^https?:\/\/[^\/%\\?#\s]+\/[^\s]*$/i;var bb=function(a,b,c,d){if(Q[c+"EventListener"])Q[c+"EventListener"](a,b,!1);else if(Q[d+"tachEvent"])Q[d+"tachEvent"]("on"+a,b)},eb=function(a){var b=cb;if("complete"!==R[ra])try{b()}catch(c){}db(a)},db=function(a){if("complete"===R[ra])a();else{var b=!1,c=function(){if(!b)return b=!0,a[N](this,arguments)};Q.addEventListener?(Q.addEventListener("load",c,!1),Q.addEventListener("DOMContentLoaded",c,!1)):Q.attachEvent&&(Q.attachEvent("onreadystatechange",function(){"complete"===R[ra]&&c[N](this,arguments)}),
Q.attachEvent("onload",c))}},fb=function(a){for(;a.firstChild;)a[wa](a.firstChild)},gb={button:!0,div:!0,span:!0};var Y;Y=S(Q,"___jsl",T());S(Y,"I",0);S(Y,"hel",10);var hb=function(a){return Y.dpo?Y.h:X(a,"jsh",Y.h)},ib=function(a){var b=S(Y,"sws",[]);b[s][N](b,a)},jb=function(a){return S(Y,"watt",T())[a]},kb=function(a){var b=S(Y,"PQ",[]);Y.PQ=[];var c=b[D];if(0===c)a();else for(var d=0,e=function(){++d===c&&a()},f=0;f<c;f++)b[f](e)},mb=function(a){return S(S(Y,"H",T()),a,T())};var nb=S(Y,"perf",T()),ob=S(nb,"g",T()),pb=S(nb,"i",T());S(nb,"r",[]);T();T();var qb=function(a,b,c){var d=nb.r;"function"===typeof d?d(a,b,c):d[s]([a,b,c])},rb=function(a,b,c){ob[a]=!b&&ob[a]||c||(new Date)[ua]();qb(a)},tb=function(a,b,c){b&&0<b[D]&&(b=sb(b),c&&0<c[D]&&(b+="___"+sb(c)),28<b[D]&&(b=b[B](0,28)+(b[D]-28)),c=b,b=S(pb,"_p",T()),S(b,c,T())[a]=(new Date)[ua](),qb(a,"_p",c))},sb=function(a){return a[P]("__")[v](/\./g,"_")[v](/\-/g,"_")[v](/\,/g,"_")};var ub=T(),vb=[],wb=function(a){throw Error("Bad hint"+(a?": "+a:""));};vb[s](["jsl",function(a){for(var b in a)if(U(a,b)){var c=a[b];"object"==typeof c?Y[b]=S(Y,b,[])[ma](c):S(Y,b,c)}if(b=a.u)a=S(Y,"us",[]),a[s](b),(b=/^https:(.*)$/[ja](b))&&a[s]("http:"+b[1])}]);var xb=/^(\/[a-zA-Z0-9_\-]+)+$/,yb=/^[a-zA-Z0-9\-_\.!]+$/,zb=/^gapi\.loaded_[0-9]+$/,Ab=/^[a-zA-Z0-9,._-]+$/,Eb=function(a,b,c,d){var e=a[F](";"),f=ub[e[ia]()],g=null;f&&(g=f(e,b,c,d));if(b=g)b=g,c=b[qa](Bb),d=b[qa](Cb),b=!!d&&1===d[D]&&Db[t](b)&&!!c&&1===c[D];b||wb(a);return g},Hb=function(a,b,c,d){a=Fb(a);zb[t](c)||wb("invalid_callback");b=Gb(b);d=d&&d[D]?Gb(d):null;var e=function(a){return aa(a)[v](/%2C/g,",")};return[aa(a.S)[v](/%2C/g,",")[v](/%2F/g,"/"),"/k=",e(a.version),"/m=",e(b),d?"/exm="+
e(d):"","/rt=j/sv=1/d=1/ed=1",a.G?"/am="+e(a.G):"",a.H?"/rs="+e(a.H):"","/cb=",e(c)][P]("")},Fb=function(a){"/"!==a[na](0)&&wb("relative path");for(var b=a[M](1)[F]("/"),c=[];b[D];){a=b[ia]();if(!a[D]||0==a[x]("."))wb("empty/relative directory");else if(0<a[x]("=")){b.unshift(a);break}c[s](a)}a={};for(var d=0,e=b[D];d<e;++d){var f=b[d][F]("="),g=fa(f[0]),h=fa(f[1]);2==f[D]&&g&&h&&(a[g]=a[g]||h)}b="/"+c[P]("/");xb[t](b)||wb("invalid_prefix");c=Ib(a,"k",!0);d=Ib(a,"am");a=Ib(a,"rs");return{S:b,version:c,
G:d,H:a}},Gb=function(a){for(var b=[],c=0,d=a[D];c<d;++c){var e=a[c][v](/\./g,"_")[v](/-/g,"_");Ab[t](e)&&b[s](e)}return b[P](",")},Ib=function(a,b,c){a=a[b];!a&&c&&wb("missing: "+b);if(a){if(yb[t](a))return a;wb("invalid: "+b)}return null},Db=/^https?:\/\/[a-z0-9_.-]+\.google\.com(:\d+)?\/[a-zA-Z0-9_.,!=\-\/]+$/,Cb=/\/cb=/g,Bb=/\/\//g,Jb=function(){var a=hb(Ha[L]);if(!a)throw Error("Bad hint");return a};
ub.m=function(a,b,c,d){(a=a[0])||wb("missing_hint");return"https://apis.google.com"+Hb(a,b,c,d)};var Kb=decodeURI("%73cript"),Lb=function(a,b){for(var c=[],d=0;d<a[D];++d){var e=a[d];e&&0>Ka[I](b,e)&&c[s](e)}return c},Nb=function(a){"loading"!=R[ra]?Mb(a):R.write("<"+Kb+' src="'+encodeURI(a)+'"></'+Kb+">")},Mb=function(a){var b=R[z](Kb);b[A]("src",a);b.async="true";(a=R[va](Kb)[0])?a[O].insertBefore(b,a):(R.head||R.body||R.documentElement)[ha](b)},Ob=function(a,b){var c=b&&b._c;if(c)for(var d=0;d<vb[D];d++){var e=vb[d][0],f=vb[d][1];f&&U(c,e)&&f(c[e],a,b)}},Qb=function(a,b){Pb(function(){var c;
c=b===hb(Ha[L])?S(W,"_",T()):T();c=S(mb(b),"_",c);a(c)})},Sb=function(a,b){var c=b||{};"function"==typeof b&&(c={},c.callback=b);Ob(a,c);var d=a?a[F](":"):[],e=c.h||Jb(),f=S(Y,"ah",T());if(f["::"]&&d[D]){for(var g=[],h=null;h=d[ia]();){var k=h[F]("."),k=f[h]||f[k[1]&&"ns:"+k[0]||""]||e,n=g[D]&&g[g[D]-1]||null,m=n;n&&n.hint==k||(m={hint:k,K:[]},g[s](m));m.K[s](h)}var p=g[D];if(1<p){var y=c.callback;y&&(c.callback=function(){0==--p&&y()})}for(;d=g[ia]();)Rb(d.K,c,d.hint)}else Rb(d||[],c,e)},Rb=function(a,
b,c){a=La(a)||[];var d=b.callback,e=b.config,f=b.timeout,g=b.ontimeout,h=null,k=!1;if(f&&!g||!f&&g)throw"Timeout requires both the timeout parameter and ontimeout parameter to be set";var n=S(mb(c),"r",[]).sort(),m=S(mb(c),"L",[]).sort(),p=[][ma](n),y=function(a,b){if(k)return 0;Q.clearTimeout(h);m[s][N](m,u);var d=((W||{}).config||{})[Ba];d?d(e):e&&S(Y,"cu",[])[s](e);if(b){tb("me0",a,p);try{Qb(b,c)}finally{tb("me1",a,p)}}return 1};0<f&&(h=Q.setTimeout(function(){k=!0;g()},f));var u=Lb(a,m);if(u[D]){var u=
Lb(a,n),w=S(Y,"CP",[]),ca=w[D];w[ca]=function(a){if(!a)return 0;tb("ml1",u,p);var b=function(b){w[ca]=null;y(u,a)&&kb(function(){d&&d();b()})},c=function(){var a=w[ca+1];a&&a()};0<ca&&w[ca-1]?w[ca]=function(){b(c)}:b(c)};if(u[D]){var lb="loaded_"+Y.I++;W[lb]=function(a){w[ca](a);W[lb]=null};a=Eb(c,u,"gapi."+lb,n);n[s][N](n,u);tb("ml0",u,p);b.sync||Q.___gapisync?Nb(a):Mb(a)}else w[ca](Ia)}else y(u)&&d&&d()};var Pb=function(a){if(Y.hee&&0<Y.hel)try{return a()}catch(b){Y.hel--,Sb("debug_error",function(){try{l.___jsl.hefn(b)}catch(a){throw b;}})}else return a()};W.load=function(a,b){return Pb(function(){return Sb(a,b)})};var Tb=function(a){var b=l.___jsl=l.___jsl||{};b[a]=b[a]||[];return b[a]},Ub=function(a){var b=l.___jsl=l.___jsl||{};b.cfg=!a&&b.cfg||{};return b.cfg},Vb=function(a){return"object"===typeof a&&/\[native code\]/[t](a[s])},Wb=function(a,b){if(b)for(var c in b)b.hasOwnProperty(c)&&(a[c]&&b[c]&&"object"===typeof a[c]&&"object"===typeof b[c]&&!Vb(a[c])&&!Vb(b[c])?Wb(a[c],b[c]):b[c]&&"object"===typeof b[c]?(a[c]=Vb(b[c])?[]:{},Wb(a[c],b[c])):a[c]=b[c])},Xb=function(a){if(a&&!/^\s+$/[t](a)){for(;0==a[K](a[D]-
1);)a=a[M](0,a[D]-1);var b;try{b=l[oa].parse(a)}catch(c){}if("object"===typeof b)return b;try{b=(new Function("return ("+a+"\n)"))()}catch(d){}if("object"===typeof b)return b;try{b=(new Function("return ({"+a+"\n})"))()}catch(e){}return"object"===typeof b?b:{}}},Yb=function(a){Ub(!0);var b=l.___gcfg,c=Tb("cu");if(b&&b!==l.___gu){var d={};Wb(d,b);c[s](d);l.___gu=b}var b=Tb("cu"),e=q.scripts||q[va]("script")||[],d=[],f=[];f[s][N](f,Tb("us"));for(var g=0;g<e[D];++g)for(var h=e[g],k=0;k<f[D];++k)h.src&&
0==h.src[x](f[k])&&d[s](h);0==d[D]&&0<e[D]&&e[e[D]-1].src&&d[s](e[e[D]-1]);for(e=0;e<d[D];++e)d[e][J]("gapi_processed")||(d[e][A]("gapi_processed",!0),(f=d[e])?(g=f.nodeType,f=3==g||4==g?f.nodeValue:f.textContent||f.innerText||f.innerHTML||""):f=void 0,(f=Xb(f))&&b[s](f));a&&(d={},Wb(d,a),c[s](d));d=Tb("cd");a=0;for(b=d[D];a<b;++a)Wb(Ub(),d[a]);d=Tb("ci");a=0;for(b=d[D];a<b;++a)Wb(Ub(),d[a]);a=0;for(b=c[D];a<b;++a)Wb(Ub(),c[a])},Z=function(a){if(!a)return Ub();a=a[F]("/");for(var b=Ub(),c=0,d=a[D];b&&
"object"===typeof b&&c<d;++c)b=b[a[c]];return c===a[D]&&void 0!==b?b:void 0},Zb=function(a,b){var c=a;if("string"===typeof a){for(var d=c={},e=a[F]("/"),f=0,g=e[D];f<g-1;++f)var h={},d=d[e[f]]=h;d[e[f]]=b}Yb(c)};var $b=function(){var a=l.__GOOGLEAPIS;a&&(a.googleapis&&!a["googleapis.config"]&&(a["googleapis.config"]=a.googleapis),S(Y,"ci",[])[s](a),l.__GOOGLEAPIS=void 0)};var ac={callback:1,clientid:1,cookiepolicy:1,openidrealm:-1,requestvisibleactions:1,scope:1},bc=!1,cc=T(),dc=function(){if(!bc){for(var a=q[va]("meta"),b=0;b<a[D];++b){var c=a[b].name[Da]();if(0==c.lastIndexOf("google-signin-",0)){var c=c[M](14),d=a[b].content;ac[c]&&d&&(cc[c]=d)}}if(l.self!==l.top){var a=q[G][C](),e;for(e in ac)0<ac[e]&&(b=X(a,e,""))&&(cc[e]=b)}bc=!0}e=T();V(cc,e);return e},ec=function(a){return!!(a.clientid&&a.scope&&a.callback)};var fc=l.console,gc=function(a){fc&&fc.log&&fc.log(a)};var hc=function(){return!!Y.oa},ic=function(){};var $=S(Y,"rw",T()),jc=function(a){for(var b in $)a($[b])},kc=function(a,b){var c=$[a];c&&c.state<b&&(c.state=b)};var lc;var mc=/^https?:\/\/(?:\w|[\-\.])+\.google\.(?:\w|[\-:\.])+(?:\/[^\?\#]*)?\/u\/(\d)\//,nc=/^https?:\/\/(?:\w|[\-\.])+\.google\.(?:\w|[\-:\.])+(?:\/[^\?\#]*)?\/b\/(\d{10,})\//,oc=function(a){var b=Z("googleapis.config/sessionIndex");null==b&&(b=l.__X_GOOG_AUTHUSER);if(null==b){var c=l.google;c&&(b=c.authuser)}null==b&&(a=a||l[G][L],b=X(a,"authuser")||null,null==b&&(b=(b=a[qa](mc))?b[1]:null));return null==b?null:r(b)},pc=function(a){var b=Z("googleapis.config/sessionDelegate");null==b&&(b=(a=(a||l[G][L])[qa](nc))?
a[1]:null);return null==b?null:r(b)};var qc=function(){};var rc=function(){this.b=[];this.n=[];this.N=[];this.k=[];this.k[0]=128;for(var a=1;64>a;++a)this.k[a]=0;this.reset()};(function(){function a(){}a.prototype=qc[E];rc.Z=qc[E];rc.prototype=new a})();rc[E].reset=function(){this.b[0]=1732584193;this.b[1]=4023233417;this.b[2]=2562383102;this.b[3]=271733878;this.b[4]=3285377520;this.o=this.i=0};
var sc=function(a,b,c){c||(c=0);var d=a.N;if("string"==typeof b)for(var e=0;16>e;e++)d[e]=b[K](c)<<24|b[K](c+1)<<16|b[K](c+2)<<8|b[K](c+3),c+=4;else for(e=0;16>e;e++)d[e]=b[c]<<24|b[c+1]<<16|b[c+2]<<8|b[c+3],c+=4;for(e=16;80>e;e++){var f=d[e-3]^d[e-8]^d[e-14]^d[e-16];d[e]=(f<<1|f>>>31)&4294967295}b=a.b[0];c=a.b[1];for(var g=a.b[2],h=a.b[3],k=a.b[4],n,e=0;80>e;e++)40>e?20>e?(f=h^c&(g^h),n=1518500249):(f=c^g^h,n=1859775393):60>e?(f=c&g|h&(c|g),n=2400959708):(f=c^g^h,n=3395469782),f=(b<<5|b>>>27)+f+
k+n+d[e]&4294967295,k=h,h=g,g=(c<<30|c>>>2)&4294967295,c=b,b=f;a.b[0]=a.b[0]+b&4294967295;a.b[1]=a.b[1]+c&4294967295;a.b[2]=a.b[2]+g&4294967295;a.b[3]=a.b[3]+h&4294967295;a.b[4]=a.b[4]+k&4294967295};rc[E].update=function(a,b){void 0===b&&(b=a[D]);for(var c=b-64,d=0,e=this.n,f=this.i;d<b;){if(0==f)for(;d<=c;)sc(this,a,d),d+=64;if("string"==typeof a)for(;d<b;){if(e[f]=a[K](d),++f,++d,64==f){sc(this,e);f=0;break}}else for(;d<b;)if(e[f]=a[d],++f,++d,64==f){sc(this,e);f=0;break}}this.i=f;this.o+=b};var tc=function(){this.p=new rc};tc[E].reset=function(){this.p.reset()};var Ac=function(){var a;uc?(a=new Q.Uint32Array(1),vc.getRandomValues(a),a=Number("0."+a[0])):(a=wc,a+=ea(xc[B](0,20),16),xc=yc(xc),a/=zc+Math.pow(16,20));return a},vc=Q.crypto,uc=!1,Bc=0,Cc=0,wc=1,zc=0,xc="",Dc=function(a){a=a||Q.event;var b=a.screenX+a.clientX<<16,b=b+(a.screenY+a.clientY),b=(new Date)[ua]()%1E6*b;wc=wc*b%zc;0<Bc&&++Cc==Bc&&bb("mousemove",Dc,"remove","de")},yc=function(a){var b=new tc;a=unescape(aa(a));for(var c=[],d=0,e=a[D];d<e;++d)c[s](a[K](d));b.p[Ba](c);a=b.p;b=[];d=8*a.o;
56>a.i?a[Ba](a.k,56-a.i):a[Ba](a.k,64-(a.i-56));for(c=63;56<=c;c--)a.n[c]=d&255,d/=256;sc(a,a.n);for(c=d=0;5>c;c++)for(e=24;0<=e;e-=8)b[d]=a.b[c]>>e&255,++d;a="";for(c=0;c<b[D];c++)a+="0123456789ABCDEF"[na](Math.floor(b[c]/16))+"0123456789ABCDEF"[na](b[c]%16);return a},uc=!!vc&&"function"==typeof vc.getRandomValues;uc||(zc=1E6*(screen[ka]*screen[ka]+screen[Ca]),xc=yc(R.cookie+"|"+R[G]+"|"+(new Date)[ua]()+"|"+Math.random()),Bc=Z("random/maxObserveMousemove")||0,0!=Bc&&bb("mousemove",Dc,"add","at"));var Ec=function(){var a=Y.onl;if(!a){a=T();Y.onl=a;var b=T();a.e=function(a){var d=b[a];d&&(delete b[a],d())};a.a=function(a,d){b[a]=d};a.r=function(a){delete b[a]}}return a},Fc=function(a,b){var c=b.onload;return"function"===typeof c?(Ec().a(a,c),c):null},Gc=function(a){Ua(/^\w+$/[t](a),"Unsupported id - "+a);Ec();return'onload="window.___jsl.onl.e(&#34;'+a+'&#34;)"'},Hc=function(a){Ec().r(a)};var Ic={allowtransparency:"true",frameborder:"0",hspace:"0",marginheight:"0",marginwidth:"0",scrolling:"no",style:"",tabindex:"0",vspace:"0",width:"100%"},Jc={allowtransparency:!0,onload:!0},Kc=0,Lc=function(a){Ua(!a||ab[t](a),"Illegal url for new iframe - "+a)},Mc=function(a,b,c,d,e){Lc(c.src);var f,g=Fc(d,c),h=g?Gc(d):"";try{f=a[z]('<iframe frameborder="'+Ra(r(c.frameborder))+'" scrolling="'+Ra(r(c.scrolling))+'" '+h+' name="'+Ra(r(c.name))+'"/>')}catch(k){f=a[z]("iframe"),g&&(f.onload=function(){f.onload=
null;g[I](this)},Hc(d))}for(var n in c)a=c[n],"style"===n&&"object"===typeof a?V(a,f[H]):Jc[n]||f[A](n,r(a));(n=e&&e.beforeNode||null)||e&&e.dontclear||fb(b);b.insertBefore(f,n);f=n?n.previousSibling:b.lastChild;c.allowtransparency&&(f.allowTransparency=!0);return f};var Nc=/^:[\w]+$/,Oc=/:([a-zA-Z_]+):/g,Pc=function(a,b){if(!lc||Z("oauth-flow/authAware")){var c=oc()||"0",d=pc(),e;e=oc(void 0)||c;var f=pc(void 0),g="";e&&(g+="u/"+e+"/");f&&(g+="b/"+f+"/");e=g||null;f=Z("oauth-flow/authAware")?"isLoggedIn":"googleapis.config/signedIn";(g=(f=!1===Z(f))?"_/im/":"")&&(e="");var h=Z("iframes/:socialhost:"),k=Z("iframes/:im_socialhost:");lc={socialhost:h,ctx_socialhost:f?k:h,session_index:c,session_delegate:d,session_prefix:e,im_prefix:g}}return lc[b]||""};var Qc={"\b":"\\b","\t":"\\t","\n":"\\n","\f":"\\f","\r":"\\r",'"':'\\"',"\\":"\\\\"},Rc=function(a){var b,c,d;b=/[\"\\\x00-\x1f\x7f-\x9f]/g;if(void 0!==a){switch(typeof a){case "string":return b[t](a)?'"'+a[v](b,function(a){var b=Qc[a];if(b)return b;b=a[K]();return"\\u00"+Math.floor(b/16)[C](16)+(b%16)[C](16)})+'"':'"'+a+'"';case "number":return isFinite(a)?r(a):"null";case "boolean":case "null":return r(a);case "object":if(!a)return"null";b=[];if("number"===typeof a[D]&&!a.propertyIsEnumerable("length")){d=
a[D];for(c=0;c<d;c+=1)b[s](Rc(a[c])||"null");return"["+b[P](",")+"]"}for(c in a)!/___$/[t](c)&&U(a,c)&&"string"===typeof c&&(d=Rc(a[c]))&&b[s](Rc(c)+":"+d);return"{"+b[P](",")+"}"}return""}},Sc=function(a){if(!a)return!1;if(/^[\],:{}\s]*$/[t](a[v](/\\["\\\/b-u]/g,"@")[v](/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g,"]")[v](/(?:^|:|,)(?:\s*\[)+/g,"")))try{return eval("("+a+")")}catch(b){}return!1},Tc=!1;try{Tc=!!l[oa]&&'["a"]'===l[oa].stringify(["a"])&&"a"===l[oa].parse('["a"]')[0]}catch(Uc){}
var Vc=function(a){try{return l[oa].parse(a)}catch(b){return!1}},Wc=Tc?l[oa].stringify:Rc,Xc=Tc?Vc:Sc;var Yc=function(a){var b;a[qa](/^https?%3A/i)&&(b=fa(a));return $a(q,b?b:a)},Zc=function(a){a=a||"canonical";for(var b=q[va]("link"),c=0,d=b[D];c<d;c++){var e=b[c],f=e[J]("rel");if(f&&f[Da]()==a&&(e=e[J]("href"))&&(e=Yc(e))&&null!=e[qa](/^https?:\/\/[\w\-\_\.]+/i))return e}return l[G][L]};var $c={post:!0},ad={style:"position:absolute;top:-10000px;width:450px;margin:0px;border-style:none"},bd="onPlusOne _ready _close _open _resizeMe _renderstart oncircled drefresh erefresh".split(" "),cd=S(Y,"WI",T()),dd=function(a,b,c){var d,e;d=e=a;"plus"==a&&b[za]&&(e=a+"_"+b[za],d=a+"/"+b[za]);(e=Z("iframes/"+e+"/url"))||(e=":socialhost:/:session_prefix:_/widget/render/"+d);d=$a(R,e[v](Oc,Pc));var f="iframes/"+a+"/params/";e={};V(b,e);var g=Z("lang")||Z("gwidget/lang");g&&(e.hl=g);$c[a]||(e.origin=
l[G].origin||l[G][xa]+"//"+l[G].host);e.exp=Z(f+"exp");if(f=Z(f+"location"))for(g=0;g<f[D];g++){var h=f[g];e[h]=Q[G][h]}switch(a){case "plus":case "follow":f=e[L];g=b[za]?void 0:"publisher";f=(f="string"==typeof f?f:void 0)?Yc(f):Zc(g);e.url=f;delete e[L];break;case "plusone":f=(f=b[L])?Yc(f):Zc();e.url=f;f=b.db;g=Z();null==f&&g&&(f=g.db,null==f&&(f=g.gwidget&&g.gwidget.db));e.db=f||void 0;f=b.ecp;g=Z();null==f&&g&&(f=g.ecp,null==f&&(f=g.gwidget&&g.gwidget.ecp));e.ecp=f||void 0;delete e[L];break;
case "signin":e.url=Zc()}Y.ILI&&(e.iloader="1");delete e["data-onload"];delete e.rd;e.gsrc=Z("iframes/:source:");f=Z("inline/css");"undefined"!==typeof f&&0<c&&f>=c&&(e.ic="1");f=/^#|^fr-/;c={};for(var k in e)U(e,k)&&f[t](k)&&(c[k[v](f,"")]=e[k],delete e[k]);k="q"==Z("iframes/"+a+"/params/si")?e:c;var f=dc(),n;for(n in f)!U(f,n)||U(e,n)||U(c,n)||(k[n]=f[n]);n=[][ma](bd);(k=Z("iframes/"+a+"/methods"))&&"object"===typeof k&&Ja[t](k[s])&&(n=n[ma](k));for(var m in b)U(b,m)&&/^on/[t](m)&&("plus"!=a||"onconnect"!=
m)&&(n[s](m),delete e[m]);delete e.callback;c._methods=n[P](",");return Za(d,e,c)},ed=["style","data-gapiscan"],gd=function(a){for(var b=T(),c=0!=a[pa][Da]()[x]("g:"),d=0,e=a[Aa][D];d<e;d++){var f=a[Aa][d],g=f.name,h=f.value;0<=Ka[I](ed,g)||c&&0!=g[x]("data-")||"null"===h||"specified"in f&&!f.specified||(c&&(g=g[B](5)),b[g[Da]()]=h)}a=a[H];(c=fd(a&&a[Ca]))&&(b.height=r(c));(a=fd(a&&a[ka]))&&(b.width=r(a));return b},fd=function(a){var b=void 0;"number"===typeof a?b=a:"string"===typeof a&&(b=ea(a,10));
return b},id=function(){var a=Y.drw;jc(function(b){if(a!==b.id&&4!=b.state){var c=b.id,d=b[sa],e=b.url;b=b.userParams;var f=R[la](c);if(f){var g=dd(d,b,0);g?(f=f[O],e[v](/\#.*/,"")[v](/(\?|&)ic=1/,"")!==g[v](/\#.*/,"")[v](/(\?|&)ic=1/,"")&&(b.dontclear=!0,b.rd=!0,b.ri=!0,ga(b,d),hd(f,b),(d=$[f.lastChild.id])&&(d.oid=c),kc(c,4))):delete $[c]}else delete $[c]}})},jd=function(){};var kd,ld,md,nd,od,pd=/(?:^|\s)g-((\S)*)(?:$|\s)/,qd={plusone:!0,autocomplete:!0,profile:!0,signin:!0};kd=S(Y,"SW",T());ld=S(Y,"SA",T());md=S(Y,"SM",T());nd=S(Y,"FW",[]);od=null;
var sd=function(a,b){rd(void 0,!1,a,b)},rd=function(a,b,c,d){rb("ps0",!0);c=("string"===typeof c?q[la](c):c)||R;var e;e=R[ya];if(c.querySelectorAll&&(!e||8<e)){e=d?[d]:Sa(kd)[ma](Sa(ld))[ma](Sa(md));for(var f=[],g=0;g<e[D];g++){var h=e[g];f[s](".g-"+h,"g\\:"+h)}e=c.querySelectorAll(f[P](","))}else e=c[va]("*");c=T();for(f=0;f<e[D];f++){g=e[f];var k=g,h=d,n=k[pa][Da](),m=void 0;k[J]("data-gapiscan")?h=null:(0==n[x]("g:")?m=n[B](2):(k=(k=r(k.className||k[J]("class")))&&pd[ja](k))&&(m=k[1]),h=!m||!(kd[m]||
ld[m]||md[m])||h&&m!==h?null:m);h&&(qd[h]||0==g[pa][Da]()[x]("g:")||0!=Sa(gd(g))[D])&&(g[A]("data-gapiscan",!0),S(c,h,[])[s](g))}if(b)for(var p in c)for(b=c[p],d=0;d<b[D];d++)b[d][A]("data-onload",!0);for(var y in c)nd[s](y);rb("ps1",!0);if((p=nd[P](":"))||a)try{W.load(p,a)}catch(u){gc(u);return}if(td(od||{}))for(var w in c){a=c[w];y=0;for(b=a[D];y<b;y++)a[y].removeAttribute("data-gapiscan");ud(w)}else{d=[];for(w in c)for(a=c[w],y=0,b=a[D];y<b;y++)e=a[y],vd(w,e,gd(e),d,b);wd(p,d)}},xd=function(a){var b=
S(W,a,{});b.go||(b.go=function(b){return sd(b,a)},b.render=function(b,d){var e=d||{};ga(e,a);return hd(b,e)})},yd=function(a){kd[a]=!0},zd=function(a){ld[a]=!0},Ad=function(a){md[a]=!0};var ud=function(a,b){var c=jb(a);b&&c?(c(b),(c=b.iframeNode)&&c[A]("data-gapiattached",!0)):W.load(a,function(){var c=jb(a),e=b&&b.iframeNode;e&&c?(c(b),e[A]("data-gapiattached",!0)):(0,W[a].go)(e&&e[O])})},td=function(){return!1},wd=function(){},vd=function(a,b,c,d,e,f){switch(Bd(b,a,f)){case 0:a=md[a]?a+"_annotation":a;d={};d.iframeNode=b;d.userParams=c;ud(a,d);break;case 1:var g;if(b[O]){for(var h in c){if(f=U(c,h))f=c[h],f=!!f&&"object"===typeof f&&(!f[C]||f[C]===ba[E][C]||f[C]===da[E][C]);if(f)try{var k=
Wc(c[h]);600>k[D]?c[h]=k:delete c[h]}catch(n){delete c[h]}}f=!0;c.dontclear&&(f=!1);delete c.dontclear;ic();k=dd(a,c,e);h={allowPost:1,attributes:ad};h.dontclear=!f;e={};e.userParams=c;e.url=k;ga(e,a);var m;c.rd?m=b:(m=q[z]("div"),b[A]("data-gapistub",!0),m[H].cssText="position:absolute;width:450px;left:-10000px;",b[O].insertBefore(m,b));e.siteElement=m;m.id||(b=m,S(cd,a,0),f="___"+a+"_"+cd[a]++,b.id=f);b=T();b[">type"]=a;V(c,b);f=k;c=m;k=h||{};b=k[Aa]||{};Ua(!k.allowPost||!b.onload,"onload is not supported by post iframe");
h=b=f;Nc[t](b)&&(h=Z("iframes/"+h[M](1)+"/url"),Ua(!!h,"Unknown iframe url config for - "+b));f=$a(R,h[v](Oc,Pc));b=c.ownerDocument||R;m=0;do h=k.id||["I",Kc++,"_",(new Date)[ua]()][P]("");while(b[la](h)&&5>++m);Ua(5>m,"Error creating iframe id");m={};var p={};b[ya]&&9>b[ya]&&(m.hostiemode=b[ya]);V(k.queryParams||{},m);V(k.fragmentParams||{},p);var y=k.pfname,u=T();u.id=h;u.parent=b[G][xa]+"//"+b[G].host;var w=X(b[G][L],"parent"),y=y||"";!y&&w&&(w=X(b[G][L],"id",""),y=X(b[G][L],"pfname",""),y=w?y+
"/"+w:"");u.pfname=y;V(u,p);(u=X(f,"rpctoken")||m.rpctoken||p.rpctoken)||(u=p.rpctoken=k.rpctoken||r(Math.round(1E8*Ac())));k.rpctoken=u;w=b[G][L];u=T();(y=X(w,"_bsh",Y.bsh))&&(u._bsh=y);(w=hb(w))&&(u.jsh=w);k.hintInFragment?V(u,p):V(u,m);f=Za(f,m,p,k.paramsSerializer);p=T();V(Ic,p);V(k[Aa],p);p.name=p.id=h;p.src=f;k.eurl=f;if((k||{}).allowPost&&2E3<f[D]){m=Wa(f);p.src="";p["data-postorigin"]=f;f=Mc(b,c,p,h);-1!=navigator.userAgent[x]("WebKit")&&(g=f.contentWindow.document,g.open(),p=g[z]("div"),
u={},w=h+"_inner",u.name=w,u.src="",u.style="display:none",Mc(b,p,u,w,k));p=(k=m.g[0])?k[F]("&"):[];k=[];for(u=0;u<p[D];u++)w=p[u][F]("=",2),k[s]([fa(w[0]),fa(w[1])]);m.g=[];p=Xa(m);m=b[z]("form");m.action=p;m.method="POST";m.target=h;m[H].display="none";for(h=0;h<k[D];h++)p=b[z]("input"),ga(p,"hidden"),p.name=k[h][0],p.value=k[h][1],m[ha](p);c[ha](m);m.submit();m[O][wa](m);g&&g.close();g=f}else g=Mc(b,c,p,h,k);e.iframeNode=g;e.id=g[J]("id");g=e.id;c=T();c.id=g;c.userParams=e.userParams;c.url=e.url;
ga(c,e[sa]);c.state=1;$[g]=c;g=e}else g=null;g&&((e=g.id)&&d[s](e),ud(a,g))}},Bd=function(a,b,c){if(a&&1===a.nodeType&&b){if(c)return 1;if(md[b]){if(gb[a[pa][Da]()])return(a=a.innerHTML)&&a[v](/^[\s\xa0]+|[\s\xa0]+$/g,"")?0:1}else{if(ld[b])return 0;if(kd[b])return 1}}return null},hd=function(a,b){var c=b[sa];delete b[sa];var d=("string"===typeof a?q[la](a):a)||void 0;if(d){var e={},f;for(f in b)U(b,f)&&(e[f[Da]()]=b[f]);e.rd=1;(f=!!e.ri)&&delete e.ri;var g=[];vd(c,d,e,g,0,f);wd(c,g)}else gc("string"===
"gapi."+c+".render: missing element "+typeof a?a:"")};S(W,"platform",{}).go=sd;var td=function(a){for(var b=["_c","jsl","h"],c=0;c<b[D]&&a;c++)a=a[b[c]];b=hb(Ha[L]);return!a||0!=a[x]("n;")&&0!=b[x]("n;")&&a!==b},wd=function(a,b){Cd(a,b)},cb=function(a){rd(a,!0)},Dd=function(a,b){for(var c=b||[],d=0;d<c[D];++d)a(c[d]);for(d=0;d<c[D];d++)xd(c[d])};vb[s](["platform",function(a,b,c){od=c;b&&nd[s](b);Dd(yd,a);Dd(zd,c._c.annotation);Dd(Ad,c._c.bimodal);$b();Yb();if("explicit"!=Z("parsetags")){ib(a);ec(dc())&&ic();var d;c&&(a=c.callback)&&(d=Ta(a),delete c.callback);eb(function(){cb(d)})}}]);W._pl=!0;var Ed=function(a){a=(a=$[a])?a.oid:void 0;if(a){var b=R[la](a);b&&b[O][wa](b);delete $[a];Ed(a)}},jd=function(a,b,c){if(c[ka]&&c[Ca]){n:{c=c||{};if(hc()){var d=b.id;if(d){var e;e=(e=$[d])?e.state:void 0;if(1===e||4===e)break n;Ed(d)}}(e=a.nextSibling)&&e[J]&&e[J]("data-gapistub")&&(a[O][wa](e),a[H].cssText="");e=c[ka];var f=c[Ca],g=a[H];g.textIndent="0";g.margin="0";g.padding="0";g.background="transparent";g.borderStyle="none";g.cssFloat="none";g.styleFloat="none";g.lineHeight="normal";g.fontSize=
"1px";g.verticalAlign="baseline";a=a[H];a.display="inline-block";g=b[H];g.position="static";g.left=0;g.top=0;g.visibility="visible";e&&(a.width=g.width=e+"px");f&&(a.height=g.height=f+"px");c.verticalAlign&&(a.verticalAlign=c.verticalAlign);d&&kc(d,3)}b["data-csi-wdt"]=(new Date)[ua]()}};var Fd=/^\{h\:'/,Gd=/^!_/,Hd="",Cd=function(a,b){function c(){bb("message",d,"remove","de")}function d(d){var g=d.data,h=d.origin;if(Id(g,b)){var k=e;e=!1;k&&rb("rqe");Jd(a,function(){k&&rb("rqd");c();for(var a=S(Y,"RPMQ",[]),b=0;b<a[D];b++)a[b]({data:g,origin:h})})}}if(0!==b[D]){Hd=X(Ha[L],"pfname","");var e=!0;bb("message",d,"add","at");Sb(a,c)}},Id=function(a,b){a=r(a);if(Fd[t](a))return!0;var c=!1;Gd[t](a)&&(c=!0,a=a[B](2));if(!/^\{/[t](a))return!1;var d=Xc(a);if(!d)return!1;var e=d.f;if(d.s&&
e&&-1!=Ka[I](b,e)){if("_renderstart"===d.s||d.s===Hd+"/"+e+"::_renderstart")c=d.a&&d.a[c?0:1],d=R[la](e),kc(e,2),c&&d&&jd(d[O],d,c);return!0}return!1},Jd=function(a,b){Sb(a,b)};var Kd=function(a,b){this.A=a;var c=b||{};this.P=c.V;this.w=c.domain;this.B=c.path;this.Q=c.W},Ld=/^[-+/_=.:|%&a-zA-Z0-9@]*$/,Md=/^[A-Z_][A-Z0-9_]{0,63}$/;Kd[E].write=function(a,b){if(!Md[t](this.A))throw"Invalid cookie name";if(!Ld[t](a))throw"Invalid cookie value";var c=this.A+"="+a;this.w&&(c+=";domain="+this.w);this.B&&(c+=";path="+this.B);var d="number"===typeof b?b:this.P;if(0<=d){var e=new Date;e.setSeconds(e.getSeconds()+d);c+=";expires="+e.toUTCString()}this.Q&&(c+=";secure");q.cookie=c};
Kd.iterate=function(a){for(var b=q.cookie[F](/;\s*/),c=0;c<b[D];++c){var d=b[c][F]("="),e=d[ia]();a(e,d[P]("="))}};var Nd=function(a){this.T=a},Od={};Nd[E].write=function(a){Od[this.T]=a};Nd.iterate=function(a){for(var b in Od)Od.hasOwnProperty(b)&&a(b,Od[b])};var Pd="https:"===l[G][xa],Qd=Pd||"http:"===l[G][xa]?Kd:Nd,Rd=function(a){var b=a[B](1),c="",d=l[G].hostname;if(""!==b){c=ea(b,10);if(isNaN(c))return null;b=d[F](".");if(b[D]<c-1)return null;b[D]==c-1&&(d="."+d)}else d="";return{c:"S"==a[na](0),domain:d,d:c}},Sd=function(a){if(0!==a[x]("GCSC"))return null;var b={v:!1};a=a[B](4);if(!a)return b;var c=a[na](0);a=a[B](1);var d=a.lastIndexOf("_");if(-1==d)return b;var e=Rd(a[B](d+1));if(null==e)return b;a=a[M](0,d);if("_"!==a[na](0))return b;d="E"===c&&
e.c;return!d&&("U"!==c||e.c)||d&&!Pd?b:{v:!0,c:d,U:a[B](1),domain:e.domain,d:e.d}},Td=function(a){if(!a)return[];a=a[F]("=");return a[1]?a[1][F]("|"):[]},Ud=function(a){a=a[F](":");return{q:a[0][F]("=")[1],L:Td(a[1]),Y:Td(a[2]),X:Td(a[3])}},Vd=function(){var a,b=null;Qd.iterate(function(c,d){if(0===c[x]("G_AUTHUSER_")){var e=Rd(c[M](11));if(!a||e.c&&!a.c||e.c==a.c&&e.d>a.d)a=e,b=d}});if(null!==b){var c;Qd.iterate(function(b,d){var e=Sd(b);e&&e.v&&e.c==a.c&&e.d==a.d&&(c=d)});if(c){var d=Ud(c),e=d&&
d.L[Number(b)],d=d&&d.q;if(e)return{M:b,O:e,q:d}}}return null};var Wd=function(a){this.F=a};Wd[E].j=0;Wd[E].D=2;Wd[E].F=null;Wd[E].t=!1;Wd[E].J=function(){this.t||(this.j=0,this.t=!0,this.C())};Wd[E].C=function(){this.t&&(this.F()?this.j=this.D:this.j=Math.min(2*(this.j||this.D),120),l.setTimeout(Ga(this.C,this),1E3*this.j))};for(var Xd=0;64>Xd;++Xd);var Yd=null,hc=function(){return Y.oa=!0},ic=function(){Y.oa=!0;var a=Vd();(a=a&&a.M)&&Zb("googleapis.config/sessionIndex",a);Yd||(Yd=S(Y,"ss",new Wd(Zd)));a=Yd;a.J&&a.J()},Zd=function(){var a=Vd(),b=a&&a.O||null,c=a&&a.q;Sb("auth",{callback:function(){var a=Q.gapi.auth,e={client_id:c,session_state:b};a.checkSessionState(e,function(b){var c=e.session_state,h=Z("isLoggedIn"),k=c&&b||!c&&!b;h!=k&&(Zb("isLoggedIn",k),ic(),id());var n=dc();(h!=k||!b&&c)&&ec(n)&&a._pimf(n,!0)})}});return!0};rb("bs0",!0,l.gapi._bs);rb("bs1",!0);delete l.gapi._bs;})();
gapi.load("plusone",{callback:window["gapi_onload"],_c:{"jsl":{"ci":{"client":{"cors":false},"plus_layer":{"isEnabled":false},"enableMultilogin":false,"isLoggedIn":true,"iframes":{"additnow":{"methods":["launchurl"],"url":"https://apis.google.com/additnow/additnow.html?bsv\u003do"},"person":{"url":":socialhost:/:session_prefix:_/widget/render/person?usegapi\u003d1\u0026bsv\u003do"},"plus_followers":{"params":{"url":""},"url":":socialhost:/_/im/_/widget/render/plus/followers?usegapi\u003d1\u0026bsv\u003do"},"signin":{"methods":["onauth"],"params":{"url":""},"url":":socialhost:/:session_prefix:_/widget/render/signin?usegapi\u003d1\u0026bsv\u003do"},"commentcount":{"url":":socialhost:/:session_prefix:_/widget/render/commentcount?usegapi\u003d1\u0026bsv\u003do"},"page":{"url":":socialhost:/:session_prefix:_/widget/render/page?usegapi\u003d1\u0026bsv\u003do"},"hangout":{"url":"https://talkgadget.google.com/:session_prefix:talkgadget/_/widget?bsv\u003do"},"plus_circle":{"params":{"url":""},"url":":socialhost:/:session_prefix:_/widget/plus/circle?usegapi\u003d1\u0026bsv\u003do"},"card":{"url":":socialhost:/:session_prefix:_/hovercard/card?bsv\u003do"},"evwidget":{"params":{"url":""},"url":":socialhost:/:session_prefix:_/events/widget?usegapi\u003d1\u0026bsv\u003do"},"zoomableimage":{"url":"https://ssl.gstatic.com/microscope/embed/?bsv\u003do"},"follow":{"url":":socialhost:/:session_prefix:_/widget/render/follow?usegapi\u003d1\u0026bsv\u003do"},"shortlists":{"url":"?bsv\u003do"},"plus":{"url":":socialhost:/:session_prefix:_/widget/render/badge?usegapi\u003d1\u0026bsv\u003do"},"configurator":{"url":":socialhost:/:session_prefix:_/plusbuttonconfigurator?usegapi\u003d1\u0026bsv\u003do"},":socialhost:":"https://apis.google.com","post":{"params":{"url":""},"url":":socialhost:/:session_prefix::im_prefix:_/widget/render/post?usegapi\u003d1\u0026bsv\u003do"},"community":{"url":":socialhost:/:session_prefix:_/widget/render/community?usegapi\u003d1\u0026bsv\u003do"},"rbr_s":{"params":{"url":""},"url":":socialhost:/:session_prefix:_/widget/render/recobarsimplescroller?bsv\u003do"},"autocomplete":{"params":{"url":""},"url":":socialhost:/:session_prefix:_/widget/render/autocomplete?bsv\u003do"},"plus_share":{"params":{"url":""},"url":":socialhost:/:session_prefix:_/+1/sharebutton?plusShare\u003dtrue\u0026usegapi\u003d1\u0026bsv\u003do"},":source:":"3p","savetowallet":{"url":"https://clients5.google.com/s2w/o/savetowallet?bsv\u003do"},"panoembed":{"url":"https://ssl.gstatic.com/pano/embed/?bsv\u003do"},"rbr_i":{"params":{"url":""},"url":":socialhost:/:session_prefix:_/widget/render/recobarinvitation?bsv\u003do"},"appcirclepicker":{"url":":socialhost:/:session_prefix:_/widget/render/appcirclepicker?bsv\u003do"},":im_socialhost:":"https://plus.googleapis.com","savetodrive":{"methods":["save"],"url":"https://drive.google.com/savetodrivebutton?usegapi\u003d1\u0026bsv\u003do"},":signuphost:":"https://plus.google.com","plusone":{"params":{"count":"","size":"","url":""},"url":":socialhost:/:session_prefix:_/+1/fastbutton?usegapi\u003d1\u0026bsv\u003do"},"comments":{"methods":["scroll","openwindow"],"params":{"location":["search","hash"]},"url":":socialhost:/:session_prefix:_/widget/render/comments?usegapi\u003d1\u0026bsv\u003do"},"ytsubscribe":{"url":"https://www.youtube.com/subscribe_embed?usegapi\u003d1\u0026bsv\u003do"}},"isPlusUser":true,"debug":{"host":"https://apis.google.com","reportExceptionRate":0.05,"rethrowException":false},"deviceType":"desktop","inline":{"css":1},"lexps":[102,98,99,79,109,45,17,118,115,81,95,122,61,30],"oauth-flow":{"improveToastUi":false,"authAware":true,"usegapi":false,"disableOpt":true,"authUrl":"https://accounts.google.com/o/oauth2/auth","proxyUrl":"https://accounts.google.com/o/oauth2/postmessageRelay","toastCfg":"1000:3000:1000"},"report":{"host":"https://apis.google.com","rate":0.001,"apis":["iframes\\..*","gadgets\\..*","gapi\\.appcirclepicker\\.*","gapi\\.client\\..*"]},"csi":{"rate":0.01},"googleapis.config":{}},"h":"m;/_/scs/apps-static/_/js/k\u003doz.gapi.en.LB1XST98A-o.O/m\u003d__features__/am\u003dEQ/rt\u003dj/d\u003d1/rs\u003dAItRSTMY8K6hxfjT0YW-FvmJXoUp416_Ig","u":"https://apis.google.com/js/plusone.js","hee":true,"fp":"72187a246e5ca211b8c919b3d29607c786e4a0ce","dpo":false},"platform":["additnow","comments","commentcount","community","follow","page","panoembed","person","plus","plusone","post","savetodrive","shortlists","ytsubscribe","zoomableimage","savetowallet","hangout"],"fp":"72187a246e5ca211b8c919b3d29607c786e4a0ce","annotation":["interactivepost","recobar","autocomplete","profile"],"bimodal":["signin"]}});