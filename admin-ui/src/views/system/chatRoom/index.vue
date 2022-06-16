<template>
  <div style="width: 760px;height: 500px;margin-left: 400px;margin-top: 150px">
    <!-- 好友栏 -->
    <div class="wxchatBorderLeft">
      <el-row>
        <div style="padding: 10px;">
          <div style="display: inline-block;">
            <el-input placeholder="搜索" prefix-icon="el-icon-search" v-model="input2" size="mini">
            </el-input>
          </div>
          <div style="display: inline-block;">
            <i class="el-icon-plus"></i>
          </div>
        </div>
      </el-row>
      <el-row>
          <div :span="24" v-for="(value,key) in userList" :key="key" v-if="value.name!=from" style="border: 1px solid red;" @click="muenUserHlane(value.name)">
            <div style="display: inline-block;padding: 12px;">
              <img width="30" height="30" :src="getImage(value.img)">
            </div>
            <div style="display: inline-block;">
              <el-col>
                <div class="wxchatPeople">{{value.name}}</div>
              </el-col>
              <el-col>
                <div class="wxchatNews">你在干什么</div>
              </el-col>
            </div>
            <span class="el-icon-close" style="margin-left: 42px;z-index: 20" @click="close(key)"></span>
          </div>
      </el-row>
    </div>
    <!-- 聊天界面栏 -->
    <div class="wxchatBorderRightTop" v-dialogDrag>
      <div style="float: right;padding: 5px;">
        <i class="el-icon-close" style="font-size: 10px;"></i>
      </div>
      <div class="wxchatName" v-html="tofrom"></div>
      <div class="wxchatMore">
        <i class="el-icon-more"></i>
      </div>
    </div>
    <!-- 聊天界面 -->
    <div id="chatMessage" ref="nav" style="overflow-y:scroll;overflow-x:hidden;;border: 1px solid red" class="wxchatBorderRightMid">
      <!-- 消息显示-->
      <div style="margin-left: 30px;margin-top: 15px;" v-for="from in MessageList" >

        <el-row v-if="from.from==tofrom">
          <div align="center" style="padding: 15px 0;">
            <el-tag type="info" size="mini">12:56</el-tag>
          </div>
          <el-col :span="3" >
            <el-avatar shape="square" :src="squareUrl" style="width: 35px;height: 35px;"></el-avatar>
          </el-col>
          <el-col :span="12">
            <div class="chatPop1">
              <span style="line-height: 23px;">{{from.data}}</span>
            </div>
          </el-col>
        </el-row>
        <el-row v-if="from.tofrom==tofrom">
          <div align="center" style="padding: 15px 0;">
            <el-tag type="info" size="mini">12:56</el-tag>
          </div>
          <el-col :span="21">
            <div class="chatPop2">
              <span style="line-height: 23px;">{{from.data}}</span>
            </div>
          </el-col>
          <el-col :span="2">
            <el-avatar shape="square" :src="squareUrl" style="width: 35px;height: 35px;"></el-avatar>
          </el-col>
        </el-row>
      </div>

      <!-- from 消息显示 -->
<!--      <div style="margin-left: 30px;margin-top: 15px;" v-for="from in MessageList" v-if="from.tofrom==tofrom">-->
<!--        <div align="center" style="padding: 15px 0;">-->
<!--          <el-tag type="info" size="mini">12:56</el-tag>-->
<!--        </div>-->
<!--        <el-row>-->
<!--          <el-col :span="21">-->
<!--            <div class="chatPop2">-->
<!--              <span style="line-height: 23px;">{{from.data}}</span>-->
<!--            </div>-->
<!--          </el-col>-->
<!--          <el-col :span="2">-->
<!--            <el-avatar shape="square" :src="squareUrl" style="width: 35px;height: 35px;"></el-avatar>-->
<!--          </el-col>-->
<!--        </el-row>-->
<!--      </div>-->
    </div>
    <!-- 消息栏 -->
    <div class="wxchatBorderRightBottom">
      <div>
        <div class="wxchatIcon1">
          <i class="el-icon-star-off"></i>
        </div>
        <div class="wxchatIcon2">
          <i class="el-icon-folder-remove"></i>
        </div>
        <div class="wxchatIcon3">
          <i class="el-icon-scissors"></i>
        </div>
        <div class="wxchatIcon4">
          <i class="el-icon-chat-dot-round"></i>
        </div>
      </div>
      <div style="margin: -2px 15px;">
        <el-input type="textarea" :autosize="{ minRows: 3, maxRows: 3}" placeholder="" v-model="content"
                  resize="none" autofocus maxlength="500">
        </el-input>
      </div>
      <div class="sendButton">
        <el-button size="mini" @click="send">发送</el-button>
      </div>
    </div>
  </div>
</template>

<script>

  import it from '../../../element-ui/src/locale/lang/it'

  export default {

    name: "chatRoom",
    data() {
      return {
        dialogTableVisible:true,
        from:"",
        tofrom:"",
        input2:"",
        squareUrl:"",
        content:"",
        socket:null,
        userList:Map,
        MessageList:[],
      };
    },
    created() {
      this.sockeft();
    },
    methods: {
      //关闭好友对话框
      close(name){
        //TODO 清空聊天记录需要保存历史聊天记录  需要删除好友列表好友
        //清空聊天记录
        this.MessageList = this.MessageList.filter(message=>{
          return message.tofrom!=name
        })
      },
      getImage(img){
        return "https://app-1255978856.cos.ap-shanghai.myqcloud.com/"+img;
      },
      //菜单栏用户事件
      muenUserHlane(name){
        this.tofrom = name;
      },
      sockeft() {
        // let _this = this;
        // if (!window.WebSocket) {
        //   window.WebSocket = window.MozWebSocket;
        // }
        //
        // if (window.WebSocket) {
        //   //获取cookie里的用户
        //   let cookie = document.cookie;
        //   let userNameStr= cookie.substring(cookie.indexOf("username"));
        //   userNameStr = userNameStr.substring(userNameStr.indexOf("=")+1,userNameStr.indexOf(";"))
        //   _this.from = userNameStr;
        //   this.socket = new WebSocket("ws://localhost/dev-api/chat/"+userNameStr);
        //   this.socket.onmessage = function(event) {
        //
        //     console.log(event)
        //
        //     let message = JSON.parse(event.data)
        //     //200连接成功
        //     if(message.status==200){
        //       console.log("连接成功！")
        //       _this.userList = JSON.parse(message.userList);
        //     }else {
        //       let message = JSON.parse(event.data)
        //       _this.MessageList.push(message);
        //       let div = document.getElementById("chatMessage");
        //       div.scrollTop += div.scrollHeight;
        //     }
        //   };
        //   this.socket.onopen = function(event) {
        //     console.log(event+"onopen")
        //   };
        //   this.socket.onclose = function(event) {
        //
        //   };
        //   this.socket.onerror = function(event) {
        //     console.log(event)
        //   }
        // } else {
        //   alert("您的浏览器不支持WebSocket协议！");
        // }
      },
      send(){
        const message = {
          "tofrom":this.tofrom,
          "data":this.content,
        }
        if(!window.WebSocket){return;}
        if(this.socket.readyState == WebSocket.OPEN){
          this.socket.send(JSON.stringify(message));
        }else{
          alert("WebSocket 连接没有建立成功！");
        }

      }
    }
  };
</script>


<style>

  .el-dialog__header {
    padding: 20px 20px 10px;
  }

  .chatPop1 :hover {
    background-color: #FAFAFA;
  }

  .chatPop1 span {
    background-color: #fff;
    padding: 5px 8px;
    display: inline-block;
    border-radius: 10px;
    margin: 0px 0 10px 10px;
    position: relative;
    border: 1px solid #E3E3E3;
    max-width: 290px;
  }

  .chatPop1 span::after {
    content: '';
    border: 8px solid #ffffff00;
    border-right: 8px solid #fff;
    position: absolute;
    top: 8px;
    left: -16px;
  }

  .chatPop2 :hover {
    background-color: #2683f5;
  }

  .chatPop2 span {
    background-color: #2683f5;
    padding: 5px 8px;
    display: inline-block;
    border-radius: 10px;
    margin: 0px 15px 10px 10px;
    position: relative;
    border: 1px solid #E3E3E3;
    max-width: 290px;
    float: right;
    color: #fff;
  }

  .chatPop2 span::after {
    content: '';
    border: 8px solid #ffffff00;
    border-right: 8px solid #2683f5;
    position: absolute;
    top: 8px;
    right: -16px;
    transform: rotateY(180deg)
  }

  .custom {
    border: 0px solid blue;
    height: 670px;
    width: 795px;
  }

  .wxchatBorder {
    width: 795px;
    height: 670px;
    border: 1px solid red;
    margin-left: -20px;
    margin-top: -59.5px;

  }

  .wxchatBorderLeft {
    width: 250px;
    height: 670px;
    background-color: #eeebe9;
   border: 1px solid gray;
    display: inline-block;
    float: left;
    margin-left: -20px;
    margin-top: -60px;
  }

  .wxchatPeople {
    color: #000000;
    font-size: 14px;
  }

  .wxchatNews {
    color: #999;
    padding-top: 5px;
    font-size: 12px;
  }

  .wxchatName {
    color: #000000;
    font-size: 20px;
    float: left;
    padding-left: 30px;
    padding-top: 20px;
  }

  .wxchatMore {
    color: #999;
    font-size: 20px;
    float: right;
    margin-right: -15px;
    padding-top: 25px;
  }

  .wxchatBorderRight {
    max-width: 245px;
    height: 670px;
   background-color: #000;
   border: 2px solid red;
    display: inline-block;
    float: right;
    margin-right: -22px;
   margin-left: 40px;
    margin-top: -60px;
  }

  .wxchatBorderRightTop {
    width: 545px;
    height: 60px;
    background-color: #f5f5f5;
    border-bottom: 1px solid #e7e7e7;
    display: block;
    float: right;
    margin-right: -20px;
    margin-top: -30px;
  }

  .wxchatBorderRightMid {
    width: 545px;
    height: 428px;
    background-color: #f5f5f5;
    border: 2px solid deeppink;
    display: block;
    float: right;
    margin-right: -20px;
  }

  .wxchatBorderRightBottom {
    width: 545px;
    height: 152px;
    background-color: #fff;
    border: 1px solid #eee;
    display: block;
    float: right;
    margin-right: -20px;
  }

  .wxchatIcon1 {
    display: inline-block;
    padding: 8px 10px 10px 30px;
    width: 40px;
    height: 40px;
    font-size: 20px;
  }

  .wxchatIcon2 {
    display: inline-block;
    padding: 8px 10px 10px 20px;
    width: 40px;
    height: 40px;
    font-size: 20px;
  }

  .wxchatIcon3 {
    display: inline-block;
    padding: 8px 10px 10px 10px;
    width: 40px;
    height: 40px;
    font-size: 20px;
  }

  .wxchatIcon4 {
    display: inline-block;
    padding: 8px 10px 10px 0px;
    width: 40px;
    height: 40px;
    font-size: 20px;
  }

  .sendButton {
    float: right;
    margin-top: 5px;
    margin-right: 28px;
  }

</style>
