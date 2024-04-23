import 'package:flutter/material.dart';
import 'package:video_player/video_player.dart';

class VideoPlayerWidget extends StatefulWidget{
  const VideoPlayerWidget({Key? key,required this.videoUrl}) : super (key: key);
  final String videoUrl;

  @override
  State<VideoPlayerWidget> createState()=> _VideoPlayerWidgetState();

}

class  _VideoPlayerWidgetState extends State<VideoPlayerWidget>{
  late VideoPlayerController _videoPlayerController;
  late Future<void> _initializeVideoPlayerFuture;
  final Stopwatch _stopwatch = Stopwatch();
  String _loadingTime = '';

  @override
  void initState(){
    _stopwatch.start();
    _videoPlayerController=VideoPlayerController.network(widget.videoUrl);
    _initializeVideoPlayerFuture= _videoPlayerController.initialize().then((_){
      _videoPlayerController.play();
      _videoPlayerController.setLooping(true);
      setState(() {
        _loadingTime = '${_stopwatch.elapsedMilliseconds} ms';
        _stopwatch.stop();
      });
    });
    super.initState();

  }

  @override
  void dispose(){
    _videoPlayerController.dispose();
    super.dispose();
  }


  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        FutureBuilder(
          future: _initializeVideoPlayerFuture,
          builder: (context, snapshot) {
            if (snapshot.connectionState == ConnectionState.done) {
              return AspectRatio(
                aspectRatio: _videoPlayerController.value.aspectRatio,
                child: Stack(
                  alignment: Alignment.bottomCenter,
                  children: [
                    VideoPlayer(_videoPlayerController),
                    _buildPauseResumeButton(),
                    _buildLoadingTime(),
                  ],
                ),
              );
            } else {
              return const Center(
                child: CircularProgressIndicator(),
              );
            }
          },
        ),
      ],
    );
  }

  Widget _buildPauseResumeButton() {
    return Padding(
      padding: const EdgeInsets.all(8.0),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          IconButton(
            onPressed: () {
              setState(() {
                if (_videoPlayerController.value.isPlaying) {
                  _videoPlayerController.pause();
                } else {
                  _videoPlayerController.play();
                }
              });
            },
            icon: Icon(
              _videoPlayerController.value.isPlaying ? Icons.pause : Icons.play_arrow,
              color: Colors.white,
            ),
          ),
        ],
      ),
    );
  }
  Widget _buildLoadingTime() {
    return Positioned(
      bottom: 10.0,
      left: 10.0,
      child: Text(
        'Loading Time: $_loadingTime',
        style: TextStyle(color: Colors.white),
      ),
    );
  }

}