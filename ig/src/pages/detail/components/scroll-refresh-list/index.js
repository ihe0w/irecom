import React from 'react'
import Style from './index.scss'
import Carousel from '@components/carousel'
import Comments from '@components/comments'
import Avatar from '@components/avatar'
import { connect } from 'react-redux'
import {func} from "prop-types";
import InfiniteScroll from 'react-infinite-scroller';
import API from '@common/api.js'



@connect(
    store => {
        return {
            posts: store.topicList
        }
    },
    dispatch => {
        return {
            addComments: info => {
                dispatch({
                    type: 'ADD_COMMENT',
                    info
                })
            },
            topicLikeFn: info => {
                dispatch({
                    type: 'TOPIC_LIKE',
                    info
                })
            },
            addTopicList: info => {
                dispatch({
                    type: 'ADD_TOPICLIST',
                    info: info
                })
            }
        };
    }
)

class ScrollRefreshList extends React.Component {

    constructor(props){
        super(props);
        this.state = {
            hasMoreItems: true,
            postItems: this.props.posts
        }
    }
    async requestMoreRecommendations(){
        console.log("helllll");
        let recommendResponse=await API.moreRecommendations({params:{userId:1,num:3}} );
        console.log("response wwwww");
        console.log(recommendResponse.data);
        console.log("is empty");
        console.log(this.props.posts);
        let tmpPosts=this.props.posts;
        recommendResponse.data.map((post,index)=>{
            tmpPosts.push(post);
        })
        // this.props.posts.concat(recommendResponse.data);
        console.log(this.props.posts)
        console.log(typeof this.props.posts)
        this.props.addTopicList(tmpPosts)
        // this.setState(
        //     {
        //         postItems: this.props.posts
        //     }
        // )
        // this.props.posts.push({postId: 777,  postUrl: "https://www.instagram.com/p/CGIIeuaAexD/", imgUrls: ["https://images.pexels.com/photos/5561371/pexels-photo-5561371.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"], createdTime: null, updatedTime: null})
        // this.props.imgUrls
        // this.props.posts.push({postId: 777, postUrl: "https://www.instagram.com/p/CGIIeuaAexD/", imgUrls: ["https://images.pexels.com/photos/5561371/pexels-photo-5561371.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"], createdTime: null, updatedTime: null})
    }

    render() {
        console.log("hehe");
        console.log(this.props.posts);
        const loader = <div className="loader">Loading ...</div>;
        var items=[];
        console.log("posts");
        console.log(this.props.posts);
        this.props.posts.map((item,index) =>{
            console.log("item xxx")
            console.log(item)
            items.push(
                <article className="article" key={index}>
                    <div className="container">
                        <Carousel imageList={item.imgUrls} ></Carousel>
                    </div>
                </article>
            )
        }
        );
        console.log("items");
        console.log(items)

        //     for (let post in this.props.posts) {
        //     console.log(post);
        //     items.push(
        //         <Carousel imageList={post.imgUrls}></Carousel>
        //     )
        // }

        return (
            <div className={Style['dynamic-list']}>



                                    <InfiniteScroll
                                        loadMore={this.requestMoreRecommendations.bind(this)}
                                        hasMore={this.state.hasMoreItems}
                                        pageStart={0}
                                        loader={loader}
                                    >
                                                {items}

                                    </InfiniteScroll>




            </div>
        )
    }

}


const mapStateToProps = state => ({
    userInfo: state.userInfo
})

export default connect(
    mapStateToProps
)(ScrollRefreshList)
