import TitleBar from "./base/TitleBar"
import Panel from "./base/Panel";
import List from "./base/List";
import ToolBar from "./base/ToolBar";

import ArticleList from "./extension/ArticleList";
import CommentBar from "./extension/CommentBar";

import Modal from "./dialog/Modal";
import Dialog from "./dialog/Dialog";
import Toast from "./dialog/Toast";

import Scroll from "./scroll/Scroll";
import ScrollNavBar from "./scroll/ScrollNavBar";

export const components = [
    TitleBar,
    Panel,
    List,
    ToolBar,

    ArticleList,
    CommentBar,

    Modal,
    Dialog,
    Toast,

    Scroll,
    ScrollNavBar
]

const createComponent = function (Vue, comp, options) {
    // console.log('create component', comp)
    const Comp = Vue.extend(comp);
    const component = new Comp({
        data: options
    }).$mount();
    document.querySelector("body").appendChild(component.$el);

    return component;
}

const install = function (Vue) {
    console.log("install heui components")
    components.map(component => Vue.component("He"+component.name, component));

    Vue.prototype.$toast = createComponent(Vue, Toast, {});

    Vue.prototype.$createToast = function (options) {
        Vue.prototype.$toast.hide();
        return Vue.prototype.$toast.init(options);
    }
}

export default {
    TitleBar,
    Panel,
    List,
    ArticleList,
    Scroll,
    ScrollNavBar,
    install
}