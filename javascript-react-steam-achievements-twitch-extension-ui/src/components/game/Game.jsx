import React, { Component } from 'react';

export default class Game extends Component {
    constructor(props) {
        super(props);

        this.state = {
            name: props.game.name,
            image: props.game.image,
            contentColor: props.contentColor,
            twitchBackgroundColor: props.twitchBackgroundColor,
            margin: props.margin
        }
    }

    render() {
        return (
            <div>
                <div className='game' style={{
                    color: `${this.state.contentColor}`,
                    backgroundColor: `${this.state.twitchBackgroundColor}`,
                    marginTop: `${this.state.margin}`,
                    marginBottom: `${this.state.margin}`,
                    borderRadius: 3
                }}>
                    <div className='game-details'>
                        <div className='game-image'>
                            <img alt={this.state.name + " Preview Image"} src={this.state.image}></img>
                        </div>
                        <div className='game-title'>
                            {this.state.name}
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}