let canvas;
let ctx;
let dataQaAttributes;

function createCanvasAndCtx() {
    canvas = document.createElement('canvas');
    canvas.width = document.body.scrollWidth;
    canvas.height = document.body.scrollHeight;
    //canvas.style.width = '100%';
    //canvas.style.height = '100%';
    canvas.style.position = 'absolute';
    canvas.style.left = 0;
    canvas.style.top = 0;
    canvas.style.zIndex = 2147483648;
    canvas.style.pointerEvents = 'none';

    document.body.appendChild(canvas);
    ctx = canvas.getContext('2d');
}

function drawRectangle(x, y, w, h) {
    let rectangle = new Path2D();
    rectangle.rect(x, y, w, h);
    ctx.strokeStyle = "red"
    ctx.stroke(rectangle);
}

function getSize(element) {
    let _w = element.clientWidth;
    let _h = element.clientHeight;

    return { w: _w, h: _h};
}

function getPosition(element) {
    let _x = 0;
    let _y = 0;
    while (element && !isNaN(element.offsetLeft) && !isNaN(element.offsetTop) ) {
        _x += element.offsetLeft - element.scrollLeft;
        _y += element.offsetTop - element.scrollTop;
        element = element.offsetParent;
    }
    return { y: _y, x: _x };
}

function getPosition2( element ) {
    let rect = element.getBoundingClientRect();
    return {
        x: rect.left + document.body.scrollLeft,
        y: rect.top + document.body.scrollTop
    };
}

function draw(element) {
    let size = getSize(element);
    let pos = getPosition(element);

    if (isVisible(element))
        drawRectangle(pos.x, pos.y, size.w, size.h);
}

function prepareAndDraw() {
    console.log("ReDraw")
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    dataQaAttributes = document.querySelectorAll('[data-qa]');
    dataQaAttributes.forEach(function(item) {
        draw(item);
    });
}

function getRndColor() {
    let r = 255*Math.random()|0,
        g = 255*Math.random()|0,
        b = 255*Math.random()|0;
    return 'rgb(' + r + ',' + g + ',' + b + ')';
}

function isVisible(elem) {
    if (!(elem instanceof Element)) throw Error('DomUtil: elem is not an element.');
    const style = getComputedStyle(elem);
    if (style.display === 'none') return false;
    if (style.visibility !== 'visible') return false;
    if (style.opacity < 0.1) return false;
    if (elem.offsetWidth + elem.offsetHeight + elem.getBoundingClientRect().height +
        elem.getBoundingClientRect().width === 0) {
        return false;
    }
    const elemCenter   = {
        x: elem.getBoundingClientRect().left + elem.offsetWidth / 2,
        y: elem.getBoundingClientRect().top + elem.offsetHeight / 2
    };
    if (elemCenter.x < 0) return false;
    if (elemCenter.x > (document.documentElement.clientWidth || window.innerWidth)) return false;
    if (elemCenter.y < 0) return false;
    if (elemCenter.y > (document.documentElement.clientHeight || window.innerHeight)) return false;
    let pointContainer = document.elementFromPoint(elemCenter.x, elemCenter.y);
    do {
        if (pointContainer === elem) return true;
    } while (pointContainer = pointContainer.parentNode);
    return false;
}

function init() {
    console.log("Init")
    createCanvasAndCtx();
    prepareAndDraw();

    "click focus input resize scroll transitionend pointerover".split(" ").forEach(function(e) {
        window.addEventListener(e, prepareAndDraw,false);
    });

    window.addEventListener("resize", function () {
        canvas.width = document.body.scrollWidth;
        canvas.height = document.body.scrollHeight;
    }, false);
    //setInterval(prepareAndDraw, 2000);
}

init();