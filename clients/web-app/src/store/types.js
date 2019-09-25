import * as article from "./article/types"
import * as comment from "./comment/types"
import * as user from "./user/types"

const moduleTypes = {
    article,
    comment,
    user
}

for (let module in moduleTypes) {

    const types = moduleTypes[module];

    const newTypes = {
        value: module //取模块名称
    };

    for (let key in types) {
        const type = types[key];
        newTypes[key] = module + '/' + type; //取带命名空间的types
    }

    moduleTypes[module] = newTypes;
}

console.log('mapTypes', moduleTypes);

export default moduleTypes;