// 将0/1/2与性别中文互相转化
// 对象数组
export function parseArrayInt2Sex(data) {
    for (let i = 0; i < data.length; i++) {
        if (data[i].sex === 0) {
            data[i].sex = "女"
        } else if (data[i].sex === 1) {
            data[i].sex = "男"
        } else {
            data[i].sex = "未知"
        }
    }
}

// 将0/1与管理员互相转换
export function parseArrayInt2Admin(data) {
    for (let i = 0; i < data.length; i++) {
        if (data[i].admin === 0) {
            data[i].admin = "是"
        }  else {
            data[i].admin = "否"
        }
    }
}

// 单独对象
// 不改变传进来的数据
export function parseSex2IntCopy(data) {
    let newData = JSON.parse(JSON.stringify(data))
    if (newData.sex === "女" ) {
        newData.sex = "0"
    } else if (newData.sex === "男") {
        newData.sex = "1"
    } else {
        newData.sex = "2"
    }
    return newData
}

export function parseAdmin2BoolCopy(data) {
    let newData = JSON.parse(JSON.stringify(data))
    if (newData.admin === "是" ) {
        newData.admin = true
    } else {
        newData.admin =false
    }
    return newData
}