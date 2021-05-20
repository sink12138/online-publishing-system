using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Data.SqlClient;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using UploadFile;

namespace ArticleManager
{
    public partial class AddArticleForm : Form
    {
        int authorid=0;
        int articleid = 0;
        String localName,targetName;
        bool getfile = false;
        SqlConnection Sc;
        SqlDataAdapter Sda;
        DataSet Ds;
        DataTable Dt;
        public AddArticleForm()
        {
            InitializeComponent();
        }

        private void textBox3_TextChanged(object sender, EventArgs e)
        {

        }

        private void label6_Click(object sender, EventArgs e)
        {

        }

        private void comboBox1_SelectedIndexChanged(object sender, EventArgs e)
        {

        }

        private void label4_Click(object sender, EventArgs e)
        {

        }

        private void addcategory(int articleid)
        {
            string Con = "Data Source=publish.vixerunt.cn,1433\\SQLEXPRESS;Initial Catalog=ArticleManagerDatabase;Persist Security Info=True;User ID=sa;Password=BUAAgjy1521";
            Sc = new SqlConnection(Con);
            Sda = new SqlDataAdapter("select * from CategoryRelationship", Sc);
            Ds = new DataSet();
            Sda.Fill(Ds);
            Dt = Ds.Tables[0];
            SqlCommand scmd = new SqlCommand("insert into CategoryRelationship(articleid,category) values(@articleid,@category)", Sc);
            scmd.Parameters.Add(new SqlParameter("@articleid", SqlDbType.Int, 4, "articleid"));
            scmd.Parameters.Add(new SqlParameter("@category", SqlDbType.Text, 999999, "category"));
            Sda.InsertCommand = scmd;
            DataRow dr = Dt.NewRow();
            try
            {
                dr["articleid"] = articleid;
                dr["category"] = textBox6.Text;
                Dt.Rows.Add(dr);
                Sda.Update(Ds);
            }
            catch { };
        }

        private void addarticle()
        {
            string Con = "Data Source=publish.vixerunt.cn,1433\\SQLEXPRESS;Initial Catalog=ArticleManagerDatabase;Persist Security Info=True;User ID=sa;Password=BUAAgjy1521";
            Sc = new SqlConnection(Con);
            Sda = new SqlDataAdapter("select * from ActiveArticle", Sc);
            Ds = new DataSet();
            Sda.Fill(Ds);
            Dt = Ds.Tables[0];
            SqlCommand scmd = new SqlCommand("insert into ActiveArticle(title,abstract,accepted,copyright,published) values(@title,@abstract,@accepted,@copyright,@published)", Sc);
            scmd.Parameters.Add(new SqlParameter("@title", SqlDbType.VarChar, 80, "title"));
            scmd.Parameters.Add(new SqlParameter("@abstract", SqlDbType.Text, 999999, "abstract"));
            scmd.Parameters.Add(new SqlParameter("@accepted", SqlDbType.Bit, 1, "accepted"));
            scmd.Parameters.Add(new SqlParameter("@copyright", SqlDbType.Bit, 1, "copyright"));
            scmd.Parameters.Add(new SqlParameter("@published", SqlDbType.Bit, 1, "published"));
            Sda.InsertCommand = scmd;
            DataRow dr = Dt.NewRow();
            try
            {
                dr["title"] = textBox2.Text;
                dr["abstract"] = textBox3.Text;
                dr["accepted"] = 0;
                dr["copyright"] = 0;
                dr["published"] = 0;
                Dt.Rows.Add(dr);
                Sda.Update(Ds);
            }
            catch { };
        }

        private void addauthorrelationship(int articleid, int authorid)
        {
            string Con = "Data Source=publish.vixerunt.cn,1433\\SQLEXPRESS;Initial Catalog=ArticleManagerDatabase;Persist Security Info=True;User ID=sa;Password=BUAAgjy1521";
            Sc = new SqlConnection(Con);
            Sda = new SqlDataAdapter("select * from AuthorRelationship", Sc);
            Ds = new DataSet();
            Sda.Fill(Ds);
            Dt = Ds.Tables[0];
            SqlCommand scmd = new SqlCommand("insert into AuthorRelationship(articleid,authorid) values(@articleid,@authorid)", Sc);
            scmd.Parameters.Add(new SqlParameter("@articleid", SqlDbType.Int, 4, "articleid"));
            scmd.Parameters.Add(new SqlParameter("@authorid", SqlDbType.Int, 4, "authorid"));
            Sda.InsertCommand = scmd;
            DataRow dr = Dt.NewRow();
            try
            {
                dr["articleid"] = articleid;
                dr["authorid"] = authorid;
                Dt.Rows.Add(dr);
                Sda.Update(Ds);
            }
            catch { };
        }

        private void button1_Click(object sender, EventArgs e)
        {
            button1.Enabled = false;
            if(textBox2.TextLength>0 && authorid!=0 && getfile && textBox6.TextLength>0 && textBox3.TextLength>0)
            {

                addarticle();
                Sda = new SqlDataAdapter("select max(articleid) as a from ActiveArticle", Sc);
                Ds = new DataSet();
                Sda.Fill(Ds);
                articleid = Convert.ToInt32(Ds.Tables[0].Rows[0]["a"].ToString());//此处为文章数据库所有文章个数+1,即为待添加文章id
                targetName = "a" + articleid + "." + localName.Substring(localName.LastIndexOf(".") + 1, (localName.Length - localName.LastIndexOf(".") - 1));
                addcategory(articleid);
                addauthorrelationship(articleid,authorid);
                Upload up = new Upload();
                //localName = "G:\\YY\\133.docx";
              //  targetName = "a120.docx";
                up.transfer_files(localName, targetName);//上传文章
                MessageBox.Show("接收成功");
                button1.Enabled = true;
                this.Close();
            }
            else
            {
                MessageBox.Show("请将信息填写完整!");
                button1.Enabled = true;
            }
        }

        private void button2_Click(object sender, EventArgs e)
        {
            button2.Enabled = false;
            SelectAuthor sa = new SelectAuthor();
            sa.ShowDialog();
            button2.Enabled = true;
            if (sa.operationSucceed)
            {
                textBox4.Text = sa.name;
            }
            authorid = sa.authorid;
        }

        private void button4_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void AddArticleForm_Load(object sender, EventArgs e)
        {
            this.FormBorderStyle = FormBorderStyle.FixedDialog;
            this.MaximizeBox = false;
        }

        private void button3_Click(object sender, EventArgs e)
        {
            OpenFileDialog openFileDialog = new OpenFileDialog();
            openFileDialog.InitialDirectory = "c:\\";//注意这里写路径时要用c:\\而不是c:\
            openFileDialog.Filter = "(*.doc;*.docx)|*.doc;*.docx";
            openFileDialog.RestoreDirectory = true;
            openFileDialog.FilterIndex = 1;
            if (openFileDialog.ShowDialog() == DialogResult.OK)
            {
                getfile = true;
                localName = openFileDialog.FileName;
                textBox5.Text = localName;
            }
        }
    }
}
