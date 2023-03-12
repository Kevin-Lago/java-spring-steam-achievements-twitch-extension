import React, { Component } from 'react';
import ajax from 'ajax';

export default class Header extends Component {
    constructor(props) {
        super(props)

        this.state = {
            header: props.header,
            contentColor: props.contentColor,
            headerHeight: "3em",
            themeColor: props.themeColor,
            themeSize: ".2em",
            twitchBackgroundColor: props.twitchBackgroundColor
        }
    }

    render() {
        return (
            <div id="header" style={{
                backgroundColor: `${this.state.themeColor}`,
                height: `${this.state.headerHeight}`,
                color: `${this.state.contentColor}`
            }}>
                <div id="header-title-wrapper" style={{
                    display: "flex",
                    justifyContent: "center",
                    alignItems: "center",
                    backgroundColor: `${this.state.twitchBackgroundColor}`,
                    height: `calc(100% - ${this.state.themeSize})`,
                    width: `calc(100% - ${this.state.themeSize})`
                }}>
                    {this.state.header}
                </div>
            </div>
        )
    }
}