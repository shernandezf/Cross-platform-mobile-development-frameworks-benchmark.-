import 'package:flutter/material.dart';
import 'package:video_player/video_player.dart';

class VideoPlayerWidget extends StatefulWidget {
  const VideoPlayerWidget({Key? key, required this.videoUrl}) : super(key: key);
  final String videoUrl;

  @override
  State<VideoPlayerWidget> createState() => _VideoPlayerWidgetState();
}

class _VideoPlayerWidgetState extends State<VideoPlayerWidget> {
  late VideoPlayerController _videoPlayerController;
  late Future<void> _initializeVideoPlayerFuture;
  final Stopwatch _stopwatch = Stopwatch();
  String _loadingTime = '';

  @override
  void initState() {
    super.initState();
    _stopwatch.start();
    _videoPlayerController = VideoPlayerController.network(widget.videoUrl);
    _initializeVideoPlayerFuture =
        _videoPlayerController.initialize().then((_) {
      _videoPlayerController.play();
      _videoPlayerController.setLooping(true);
      _stopwatch.stop();
      setState(() {
        _loadingTime = '${_stopwatch.elapsedMilliseconds} ms';
      });
    }).catchError((error) {
      // Handle errors here
      _stopwatch.stop();
      print('Error initializing video player: $error');
    });
  }

  @override
  void dispose() {
    _videoPlayerController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('Video Player')),
      body: Center(
        child: FutureBuilder(
          future: _initializeVideoPlayerFuture,
          builder: (context, snapshot) {
            if (snapshot.connectionState == ConnectionState.done) {
              if (snapshot.hasError) {
                return Text('Error loading video');
              }
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
              return const CircularProgressIndicator();
            }
          },
        ),
      ),
    );
  }

  Widget _buildPauseResumeButton() {
    return Positioned(
      bottom: 20,
      right: 20,
      child: CircleAvatar(
        backgroundColor: Colors.black45,
        child: IconButton(
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
            _videoPlayerController.value.isPlaying
                ? Icons.pause
                : Icons.play_arrow,
            color: Colors.white,
          ),
        ),
      ),
    );
  }

  Widget _buildLoadingTime() {
    return Positioned(
      bottom: 10,
      left: 10,
      child: Text(
        'Loading Time: $_loadingTime',
        style: TextStyle(color: Colors.white),
      ),
    );
  }
}
