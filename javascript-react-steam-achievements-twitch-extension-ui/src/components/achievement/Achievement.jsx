import React, { Component } from 'react';

export default class Achievement extends Component {
    constructor(props) {
        super(props)

        this.state = {
            game: props.achievement.game,
            image: props.achievement.image,
            contentColor: props.contentColor,
            twitchBackgroundColor: props.twitchBackgroundColor,
            margin: props.margin
        }
    }

    render() {
        return (
            <div className='achievement' style={{
                color: `${this.state.contentColor}`,
                backgroundColor: `${this.state.twitchBackgroundColor}`,
                marginTop: `${this.state.margin}`,
                marginBottom: `${this.state.margin}`,
                borderRadius: 3
            }}>
                <div className='achievement-game-details'>
                    <div className='achievement-game-image'>
                        <img alt={this.state.game + " Preview Image"} src={this.state.image}></img>
                    </div>
                    <div className='achievement-game-title'>
                        {this.state.game}
                    </div>
                </div>
                <div className='achievement-progress'>
                    Test
                </div>
            </div>
        )
    }
}