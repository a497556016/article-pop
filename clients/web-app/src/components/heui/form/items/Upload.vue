<template>
    <div class="he-upload" :style="{textAlign: config.align}">
        <div class="file-list">
            <div class="file-input" :style="{height: config.height,width: config.width}" v-for="(file, index) in fileList">
                <div class="img" @click="chooseFile(index)">
                    <img v-if="file" :src="file" style="width: 100%;height: 100%"/>
                    <i v-else class="icon fa fa-plus"></i>
                </div>
                <div class="progress">
                    <div :style="{width: progress, backgroundColor: '#ff0000', height: '100%'}"></div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import Input from "./Input";
    export default {
        name: "Upload",
        components: {Input},
        props: {
            files: String|Array,
            options: {
                type: Object,
                default(){
                    return {}
                }
            }
        },
        model: {
            prop: 'files',
            event: 'change'
        },
        data(){
            return {
                progress: '0%',
                fileList: []
            }
        },
        watch: {
            files(files){
                this.calcFileList(files);
            }
        },
        computed: {
            config(){
                return Object.assign({
                    align: 'left',
                    limit: 1,
                    maxSize: 1,
                    height: '4em',
                    width: '4em',
                    uploadUrl: '',//上传文件地址
                    success: null,//上传成功回调函数
                    upload: null, //自定义上传方式
                }, this.options)
            }
        },
        mounted() {
            this.calcFileList();
        },
        methods: {
            calcFileList(){
                this.fileList = [];
                // console.log(this.files);
                if(!this.files){
                    for (let i = 0; i < this.config.limit; i++) {
                        this.fileList.push('');
                    }
                }else if(Array.isArray(this.files)){
                    let end = this.config.limit;
                    if(this.files.length < end){
                        end = this.files.length;
                    }
                    this.fileList.push(...this.files.slice(0, end));
                }else{
                    this.fileList.push(this.files);
                    for (let i = 1; i < this.config.limit; i++) {
                        this.fileList.push('');
                    }
                }

            },
            chooseFile(index){
                const fileInput = document.createElement('INPUT');
                fileInput.type = 'file';
                fileInput.multiple = false;
                fileInput.onchange = (e) => {
                    const files = fileInput.files;
                    if(files.length){
                        if((files[0].size / 1024 / 1024).toFixed(1) > this.config.maxSize){
                            this.$createToast({
                                msg: `文件超出最大限制${this.config.maxSize}M！`
                            }).show();
                            return;
                        }
                        console.log(files[0])
                        // fileData.src = files[0];
                        //开始上传
                        if(this.config.upload){
                            this.config.upload(files[0]);
                        }else if(this.config.uploadUrl){
                            this.upload(files[0])
                        }
                    }
                    fileInput.value = '';
                }
                fileInput.click();
            },
            upload(file){
                const formData = new FormData();
                formData.append("file", file);
                const xhr = new XMLHttpRequest();
                xhr.onreadystatechange = () => {
                    if (xhr.readyState === 4 && xhr.status === 200) {
                        console.log(xhr);
                        this.progress = '0%';
                        if(this.config.success){
                            this.config.success(xhr.response);
                        }
                    }
                };
                xhr.upload.addEventListener("progress", (event) => {
                    if(event.lengthComputable){
                        this.progress = Math.ceil(event.loaded * 100 / event.total) + "%";
                        console.log('上传进度',this.progress)
                    }
                }, false);
                xhr.open("POST", this.config.uploadUrl);
                xhr.send(formData);
            }
        }
    }
</script>

<style scoped lang="less">

</style>