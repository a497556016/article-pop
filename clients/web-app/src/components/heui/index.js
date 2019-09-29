import TitleBar from "./base/TitleBar"
import Panel from "./base/Panel";
import List from "./base/List";
import ListItem from "./base/ListItem";
import ToolBar from "./base/ToolBar";
import Button from "./base/Button";
import Alert from "./base/Alert";

import ArticleList from "./extension/ArticleList";
import CommentBar from "./extension/CommentBar";

import Modal from "./dialog/Modal";
import Dialog from "./dialog/Dialog";
import Toast from "./dialog/Toast";

import Form from "./form/Form";
import FormGroup from "./form/FormGroup";
import FormItem from "./form/FormItem";

import Scroll from "./scroll/Scroll";
import ScrollNavBar from "./scroll/ScrollNavBar";

import Page from "./layout/Page";
import PageHeader from "./layout/PageHeader";
import PageBody from "./layout/PageBody";
import PageFooter from "./layout/PageFooter";

export const components = [
    TitleBar,
    Panel,
    List,
    ListItem,
    ToolBar,
    Button,
    Alert,

    ArticleList,
    CommentBar,

    Modal,
    Dialog,
    Toast,

    Form,
    FormGroup,
    FormItem,

    Scroll,
    ScrollNavBar,

    Page,
    PageHeader,
    PageBody,
    PageFooter
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
        if(typeof options === 'string'){
            options = {msg: options}
        }
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