package cn.xldhome.wechat.message.resp;

import cn.xldhome.wechat.message.req.BaseMessage;
/**
 * 音乐消息
 * @author liudong
 *
 */
public class MusicMessage extends BaseMessage{
	// 音乐
		private Music Music;
	 
		public Music getMusic() {
			return Music;
		}
	 
		public void setMusic(Music music) {
			Music = music;
		}

}
