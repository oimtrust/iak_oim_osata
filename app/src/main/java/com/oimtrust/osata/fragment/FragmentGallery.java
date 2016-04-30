package com.oimtrust.osata.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.oimtrust.osata.R;
import com.oimtrust.osata.activity.DetailGalleryActivity;
import com.oimtrust.osata.adapter.GalleryItemAdapter;

public class FragmentGallery extends Fragment {
    private String[] galleryImg    = new String[]{
            "https://farm3.staticflickr.com/2855/10376831565_c327ed485a.jpg",
            "http://4.bp.blogspot.com/-xzzXwcT7hJQ/VhIuxL8K9YI/AAAAAAAAAh8/IekRlZt4i7c/s1600/Pantai%2B3%2Bwarnaa.jpg",
            "http://indotravelguides.com/wp-content/uploads/2015/02/jatim-park-2-secret-zoo.jpg",
            "http://klikhotel.com/blog/wp-content/uploads/2015/08/jatim-park-11.jpg",
            "http://www.setiatransport.com/images/selecta.jpg",
            "https://dythafitriyani.files.wordpress.com/2012/03/2656993547_1878200fc3.jpg",
            "http://surabaya.panduanwisata.id/files/2013/12/jembatan-tirta-nirwana.jpg",
            "http://www.newsth.com/ruptik/wp-content/uploads/2015/11/Gunung-Bromo.jpg",
            "http://cdn0-a.production.liputan6.static6.com/medias/669982/big/gunung-bromo121230c.jpg",
            "http://www.indonesia.go.id/images/stories/opbengkulu/objek%20bukit%20kaba-bengkulu1.jpg"
    };

    private GalleryItemAdapter itemAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View galleryRoot = inflater.inflate(R.layout.activity_fragment_gallery, container, false);

        itemAdapter = new GalleryItemAdapter(getActivity(), galleryImg);

        GridView itemGalleryGridview = (GridView) galleryRoot.findViewById(R.id.gallery_gridView);

        itemGalleryGridview.setAdapter(itemAdapter);
        itemGalleryGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DetailGalleryActivity.toDetailImageActivity(getActivity(), galleryImg[position]);
            }
        });
        return galleryRoot;
    }
}
