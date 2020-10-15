# 解释Ig是如何构建的
## 术语
topic: post
```                <div className="page-container">
                       <span className="loading"></span>
                           {
                               !this.state.showAttentionList && this.props.posts.length > 0?
                               <div className={Style['home-detail']}>
                                   <DynamicList/>
                                   <Recommend togglePostTopic={this.togglePostTopic}  followList={this.state.followList} setFollowStatus={this.setFollowStatus}/>
                               </div>
                               :
                               <div className={Style['home-detail']}>
                                   <AttentionList followList={this.state.followList} setFollowStatus={this.setFollowStatus} />
                                   {
                                       this.state.followList.length === 0?
                                           <Recommend togglePostTopic={this.togglePostTopic} followList={this.state.followList} setFollowStatus={this.setFollowStatus} />
                                           : ''
                                   }
                               </div>
                           }
                   </div>```
这段代码在pages/detail中。