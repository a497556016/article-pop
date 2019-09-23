import TitleBar from "./base/TitleBar"
import Panel from "./base/Panel";
import List from "./base/List";
import ArticleList from "./base/ArticleList";

import Scroll from "./scroll/Scroll";
import ScrollNavBar from "./scroll/ScrollNavBar";

export const components = [
    TitleBar,
    Panel,
    List,
    ArticleList,
    Scroll,
    ScrollNavBar
]

const install = function (Vue) {
    console.log("install heui components")
    components.map(component => Vue.component("He"+component.name, component));
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