import React from 'react'
import Style from './index.scss'
import Carousel from '@components/carousel'
import Comments from '@components/comments'
import Avatar from '@components/avatar'
import { connect } from 'react-redux'
import {func} from "prop-types";


@connect(
    store => {
        return {
            dynamicList: store.topicList,
            userInfo: store.userInfo
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
            }
        };
    }
)

class DynamicList extends React.Component {

    constructor(props){
        super(props);
        this.state = {}
    }

    render() {
        console.log("hehe");
        console.log(this.props.dynamicList);
        return (
            <div className={Style['dynamic-list']}>
                {
                    this.props.dynamicList.map((item,index) => {
                        return (
                        <article className="article" key={index}>
                            {/*<header className="header">*/}
                            {/*    <Avatar userInfo={item.userInfo}/>*/}
                            {/*</header>*/}
                            
                            {/*<div className="container">*/}
                            {/*    <Carousel imageList={item.topic.imgUrls}></Carousel>*/}
                            {/*</div>*/}
                            <div className="container">
                                <Carousel imageList={item.imgUrls}></Carousel>
                            </div>

                        </article>
                        )
                    })
                }
            </div>
        )
    }

}


const mapStateToProps = state => ({
    userInfo: state.userInfo
})

export default connect(
    mapStateToProps
)(DynamicList)

//                             <div className="comments-content">
//                                 <Comments
//                                     topicLikeFn={this.props.topicLikeFn}
//                                     addComments={this.props.addComments}
//                                     topicIndex={index}
//                                     createdAt={item.topic.createdTime}
//                                     discuss={item.discuss}
//                                     topicId={item.topic.topicId}
//                                     topicLike={item.topic.topicLike}
//                                     dotCounts={item.topic.topicLikeCounts}>
//                                 </Comments>
//                             </div>

// backup
// @connect(
//     store => {
//         return {
//             dynamicList: store.topicList,
//             userInfo: store.userInfo
//         }
//     },
//     dispatch => {
//         return {
//             addComments: info => {
//                 dispatch({
//                     type: 'ADD_COMMENT',
//                     info
//                 })
//             },
//             topicLikeFn: info => {
//                 dispatch({
//                     type: 'TOPIC_LIKE',
//                     info
//                 })
//             }
//         };
//     }
// )
//
// class DynamicList extends React.Component {
//
//     constructor(props){
//         super(props);
//         this.state = {}
//     }
//
//     render() {
//         console.log("hehe");
//         console.log(this.props.dynamicList);
//         return (
//             <div className={Style['dynamic-list']}>
//                 {
//                     this.props.dynamicList.map((item,index) => {
//                         return (
//                         <article className="article" key={index}>
//                             {/*<header className="header">*/}
//                             {/*    <Avatar userInfo={item.userInfo}/>*/}
//                             {/*</header>*/}
//
//                             {/*<div className="container">*/}
//                             {/*    <Carousel imageList={item.topic.imgUrls}></Carousel>*/}
//                             {/*</div>*/}
//                             <div className="container">
//                                 <Carousel imageList={item.imgUrls}></Carousel>
//                             </div>
//
//                         </article>
//                         )
//                     })
//                 }
//             </div>
//         )
//     }
//
// }
//
//
// const mapStateToProps = state => ({
//     userInfo: state.userInfo
// })
//
// export default connect(
//     mapStateToProps
// )(DynamicList)