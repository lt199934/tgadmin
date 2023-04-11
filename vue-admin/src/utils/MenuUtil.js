//动态路由
export function menuTree(data) {
    let map = {};
    let val = [];
    //生成数据对象集合
    data.forEach(it => {
        map[it.id] = it;
    })
    //生成结果集
    data.forEach(it => {
        const parent = map[it.pid];
        if (parent) {
            if (!Array.isArray(parent.children)) parent.children = [];
            parent.children.push(it);
        } else {
            val.push(it);
        }
    })
    return val;
}